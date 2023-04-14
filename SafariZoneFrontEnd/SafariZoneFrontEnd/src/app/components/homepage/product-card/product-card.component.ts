import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { OrderapiService } from 'src/app/services/orderapi.service';
import { ProductapiService } from 'src/app/services/productapi.service';
import { JsonResponse } from '../../models/JsonResponse';
import { Pokemon } from '../../models/Pokemon';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})

export class ProductCardComponent implements OnInit {

  @Input()
  isDarkMode : boolean = false;

  pokemon : Pokemon = <Pokemon>{}
  pokemonId : number = 25
  jsonResponse : JsonResponse = <JsonResponse>{};
  sprite : string = "";
  productId : number = 3;
  filterProducts : Array<any>=[];
  addToCartProductId!: number;
  cartId! : number;
  userId! : number;

  //productsInCart : Array<number> = [];

  constructor(public productapiService : ProductapiService, private router : Router, public orderApiServ : OrderapiService,
     private route : ActivatedRoute, private darkModeServ : DarkmodeService) { }


  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
      this.orderApiServ.getCartByUserId(this.userId).subscribe(responseBody => {
        if (responseBody.data != null)
        {
          this.cartId = responseBody.data.cartId;
          this.getAllAvailableProducts();
        }
      });
    });
    this.isDarkMode = this.darkModeServ.checkDarkMode();
  }

  getAllProducts(){
    this.productapiService.getAllProducts().subscribe(responseBody => {
      this.productapiService.allProducts = responseBody.data;
    });
  }

  getAllAvailableProducts() {
    this.productapiService.getAllAvailableProducts().subscribe(productsAvailable => {
      this.productapiService.allProducts = productsAvailable.data;
      this.productapiService.productsInCart = [];
      this.orderApiServ.getCartByUserId(this.userId).subscribe(responseBody => {
        responseBody.data.products.forEach((item : Product) => {
          this.productapiService.productsInCart.push(item.productId);
        });
      });
    });
  }

 getOneProductById(productId : any) {
    this.productapiService.getOnePokemonById(productId).subscribe(responseBody => {
      this.pokemon = responseBody;
      this.sprite = responseBody.sprites.front_default;
    });
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
  }

  addToCart(e : any){
    this.addToCartProductId = e.target.value;
    this.orderApiServ.getCartByUserId(this.userId).subscribe(responseBody => {
      this.cartId = responseBody.data.cartId;
      this.orderApiServ.addToCart(this.cartId, this.addToCartProductId).subscribe(responseBody => {
        this.orderApiServ.getOneCartByCartId(this.cartId).subscribe(responseBody => {
          this.productapiService.productsInCart = [];
          responseBody.data.products.forEach((item: Product) => {
            this.productapiService.productsInCart.push(item.productId);
          });
          this.orderApiServ.updateCartProductsAndTotal(responseBody.data).subscribe(responseBody => {
              this.orderApiServ.productsInCart = responseBody.data.products;
              this.orderApiServ.totalCost = responseBody.data.total;
          });
        });
      });
    });
  }

}
