import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderapiService {

  baseurl = `${environment.domain}/`

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type":"application/json"
    })
  }

  productsInCart : Array<any> = [];
  totalCost : number = 0;

  constructor(private httpCli : HttpClient) { }

  //GetEnpoints

  getAllCarts() {
    return this.httpCli.get<any>(this.baseurl + "cart/all");
  }

  getOneCartByCartId(cartId : number) {
    return this.httpCli.get<any>(this.baseurl + "cart/id/" + cartId);
  }

  getCartByUsername(username : string) {
    return this.httpCli.get<any>(this.baseurl + "cart/username/" + username);
  }

  getCartByUserId(userId : number) {
    return this.httpCli.get<any>(this.baseurl + "cart/userId/" + userId);
  }

  getOrderByUserId(userId : number) { 
    return this.httpCli.get<any>(this.baseurl + "order/userId/" + userId);
  }

  getAllOrders() {
    return this.httpCli.get<any>(this.baseurl + "order/all")
  }

  //PostEndpoints

  createCart(cart : any) {
    return this.httpCli.post<any>(this.baseurl + "cart", JSON.stringify(cart), this.httpOptions);
  }

  addToCart(cartId : number, productId : number) {
    return this.httpCli.post<any>(this.baseurl + "cart/addtocart/" + cartId + "/" + productId, JSON, this.httpOptions);
  }

  createOrder(order : any) {
    return this.httpCli.post<any>(this.baseurl + "order", JSON.stringify(order), this.httpOptions)
  }

  //PutEndpoints

  updateCartProductsAndTotal(cart : any) {
    return this.httpCli.put<any>(this.baseurl + "cart", JSON.stringify(cart), this.httpOptions);
  }

  submitCart(cartId : number) {
    return this.httpCli.put<any>(this.baseurl + "cart/" + cartId, this.httpOptions);
  }

  //DeleteEndpoints

  deleteCart(cartId : number) {
    return this.httpCli.delete<any>(this.baseurl + "cart/" + cartId);
  }
  
}
