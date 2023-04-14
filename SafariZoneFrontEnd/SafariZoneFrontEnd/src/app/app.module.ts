import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/user/login/login.component';
import { RegisterComponent } from './components/user/register/register.component';
import { UserInfoComponent } from './components/user/user-info/user-info.component';
import { CartComponent } from './components/homepage/cart/cart.component';
import { DisplayAllComponent } from './components/homepage/display-all/display-all.component';
import { SearchComponent } from './components/homepage/search/search.component';
import { CheckoutComponent } from './components/homepage/checkout/checkout.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { ProductCardComponent } from './components/homepage/product-card/product-card.component';
import { CreateProductComponent } from './components/homepage/create-product/create-product.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { OrderHistoryComponent } from './components/homepage/order-history/order-history.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserInfoComponent,
    CartComponent,
    DisplayAllComponent,
    SearchComponent,
    CheckoutComponent,
    HomepageComponent,
    ProductCardComponent,
    CreateProductComponent,
    OrderHistoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
