import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartComponent } from './components/homepage/cart/cart.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { OrderHistoryComponent } from './components/homepage/order-history/order-history.component';
import { LoginComponent } from './components/user/login/login.component';
import { RegisterComponent } from './components/user/register/register.component';
import { UserInfoComponent } from './components/user/user-info/user-info.component';

const routes: Routes = [
  {path : "", component : LoginComponent},
  {path : "home", component : HomepageComponent},
  {path : "register", component : RegisterComponent},
  {path : "profile", component : UserInfoComponent},
  {path : "cart", component : CartComponent},
  {path : "history", component : OrderHistoryComponent},
  {path : "**", redirectTo : ""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
