import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { OrderapiService } from 'src/app/services/orderapi.service';
import { ProductapiService } from 'src/app/services/productapi.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  cartObject : any;
  userId! : number;
  cartId! : number;
  confirm : boolean = false;
  isSubmitted : boolean = false;
  nothingInCart : boolean = false;

  audioTest = new Audio('../../../assets/audio/bruh_vocals2.mp3');

  @Input()
  isDarkMode : boolean = false;

  constructor(
    private orderApiService : OrderapiService,
    private route : ActivatedRoute,
    private productApiService : ProductapiService,
    private darkModeServ : DarkmodeService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    })
    this.getCartByUserId(this.userId);
    this.isDarkMode = this.darkModeServ.checkDarkMode();
  }

  submitCart(e : any) {
    this.orderApiService.getCartByUserId(this.userId).subscribe(cartToSubmit =>{
      let i =0;
      let purchased = false;
      this.cartId = cartToSubmit.data.cartId;
      while(i < cartToSubmit.data.products.length)
      {
        if (cartToSubmit.data.products[i].purchased == true)
        {
          purchased =true;
          cartToSubmit.data.products.splice(i,1);
        }
        else
        {
          i+=1;
        }
      }
      if (purchased)
      {
        this.orderApiService.productsInCart = cartToSubmit.products;
      }
      else{
      if (cartToSubmit.data.products.length != 0)
      {
        this.orderApiService.submitCart(this.cartId).subscribe(responseBody => {
          if(responseBody.success == true) {
            this.toggleAnimation();
            this.audioTest.play();
            this.nothingInCart=false;
              for(let i = 0; i < cartToSubmit.data.products.length; i++) {
                this.productApiService.purchaseProduct(cartToSubmit.data.products[i].productId).subscribe(productsToPurchase => {
                })
              }
            
            let newOrder = {
              cart : {
                cartId : this.cartId 
              }
            }
            this.orderApiService.createOrder(newOrder).subscribe(responseBody => {
              responseBody.data.cart.products[1];
              this.createCart(this.userId);
            })
          }
        });
      }
      else{
        console.log("Nothing In cart. (Insert message for user)");
        this.nothingInCart = true;
      }
    this.confirm = false;
      }
    })
  }

  getCartByUserId(userId:number){
    this.orderApiService.getCartByUserId(userId).subscribe(responseBody => {
      if (responseBody.success ==  true)
      {
        this.cartId = responseBody.data.cartId;
        this.cartObject = responseBody.data;
      }
    })
  }

  confirmSubmit(){
    this.confirm = true;
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
      this.orderApiService.updateCartProductsAndTotal(responseBody.data).subscribe(responseBody=> {
        this.orderApiService.productsInCart = responseBody.data.products;
        this.orderApiService.totalCost = responseBody.data.total;
        this.productApiService.getAllAvailableProducts().subscribe(responseBody =>
        {
          this.productApiService.allProducts = responseBody.data;
        })
      })
    })
  }

  toggleAnimation() { 
    this.isSubmitted = !this.isSubmitted;
  }
}
