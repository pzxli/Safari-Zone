import { Component, ComponentFactoryResolver, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { empty } from 'rxjs';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { OrderapiService } from 'src/app/services/orderapi.service';
import { ProductapiService } from 'src/app/services/productapi.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  @Input()
  isDarkMode : boolean = false;

  cartObject : any;
  cartId : number = 1;
  emptyCart : any = {
    "user":{
      "userId":1
    },
    "submitted":false
  }
  userId!: number;


  constructor(public orderApiService : OrderapiService, private route : ActivatedRoute,
    private darkModeServ : DarkmodeService, private productApiService : ProductapiService) { }

  ngOnInit(): void {
    //this.getCartByUsername("user");
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    })
    this.doesUserHaveCart(this.userId);
    this.isDarkMode = this.darkModeServ.checkDarkMode();
  }

  doesUserHaveCart(userId : number){
    this.orderApiService.getCartByUserId(userId).subscribe(responseBody => {
      if (responseBody.success == false) {
        this.createCart(this.userId);
      } else {
        this.getCartByUserId(this.userId);
      }
    })
  }

  getCartByUserId(userId:number){
    this.orderApiService.getCartByUserId(userId).subscribe(responseBody => {
      let i =0;
      while(i < responseBody.data.products.length)
      {
        if (responseBody.data.products[i].purchased == true)
        {
          responseBody.data.products.splice(i,1);
        }
        else
        {
          i+=1;
        }
      }
      this.orderApiService.productsInCart = responseBody.data.products;
      if (responseBody.data.products.length == 0) {
        this.orderApiService.totalCost = 0;
      } else {
        this.orderApiService.totalCost = responseBody.data.total;
      }
      this.updateCart(responseBody.data);

      //assigns cart object to cartObject
      this.cartObject = responseBody.data;
    })
  }

  getCartByUsername(username : string) {
    this.orderApiService.getCartByUsername(username).subscribe(responseBody => {
      this.orderApiService.productsInCart = responseBody.data.products;
      if (responseBody.data.products.length == 0) {
        this.orderApiService.totalCost = 0;
      } else {
        this.orderApiService.totalCost = responseBody.data.total;
      }
      this.updateCart(responseBody.data);

      //assigns cart object to cartObject
      this.cartObject = responseBody.data;
      
    })
  }

  updateCart(cart : any) {
    this.orderApiService.updateCartProductsAndTotal(cart).subscribe(responseBody => {
      if(this.orderApiService.productsInCart.length == 0) {
        this.orderApiService.totalCost = 0.0; 
      } else {
        this.orderApiService.totalCost = responseBody.data.total;
      }
    })
  }

  deleteButton(e : any) {
    let prodId : number = e.target.value;
    for (let i = 0; i < this.orderApiService.productsInCart.length; i++){
      if(this.orderApiService.productsInCart[i].productId == prodId){
        this.orderApiService.productsInCart.splice(i,1);
      }
    }
    this.cartObject.products=this.orderApiService.productsInCart;
    this.productApiService.productsInCart.splice(this.productApiService.productsInCart.findIndex(product => product == prodId),1);
    this.updateCart(this.cartObject);
  }

  createCart(userIdForUser : number){
    let emptyCart : any = {
      "user":{
        "userId":userIdForUser
      },
      "submitted":false
    }
    this.orderApiService.createCart(emptyCart).subscribe(responseBody => {
      this.cartObject = responseBody.data;
      this.cartId = responseBody.data.cartId;
      this.orderApiService.productsInCart = responseBody.data.products;
    })
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
  }
}
