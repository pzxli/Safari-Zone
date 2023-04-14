import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { OrderapiService } from 'src/app/services/orderapi.service';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';
import { Pokemon } from '../../models/Pokemon';
import { Product } from '../../models/Product';
import { UserInfoComponent } from '../../user/user-info/user-info.component';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  isDarkMode : boolean = false;
  darkModeString : string = "";
  userObjectIsEmpty : boolean = true;
  lastProductIsEmpty : boolean = true;

  userId! : number;
  orders : Array<any> = [];
  allOrders : Array<any> = [];
  products : Array<any> = [];
  roleId! : number;
  ifAdmin : boolean = false;

  username: string = "";
  password: string = "";
  firstname: string = "";
  lastname: string = "";
  email: string = "";
  shipping: string = "";

  lastPokemon : Pokemon = <Pokemon>{};
  lastProduct : Product = <Product>{};

  User:any = {
    id:this.userId,
    username: this.username,
    password: this.password,
    firstName: this.firstname,
    lastName: this.lastname,
    email: this.email,
    shippingAddress: this.shipping,
    role: {roleId : 1}
  }

  @ViewChild(UserInfoComponent)
  private userInfoCom!: UserInfoComponent;

  constructor(private orderApiService : OrderapiService, private route : ActivatedRoute,
    private sessionApi : SessionapiService, private router : Router, public userApi : UserapiService,
    private darkModeServ : DarkmodeService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    })
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == false){
        this.router.navigate(['']);
      } else {
        this.getUserObjectCheckForRole();
      }
    });

    
    this.getUser();
    this.getAllOrdersByUserId(this.userId);
    this.isDarkMode = this.darkModeServ.checkDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

  getUser(){
    this.userApi.getById(this.userId).subscribe(response=>{
      this.userApi.User = response.data;
      this.userObjectIsEmpty = false;
    })
  }

  getAllOrdersByUserId(userId : number) {
    this.orderApiService.getOrderByUserId(userId).subscribe(responseBody => {
      if (responseBody.success == true)
      {
        this.orders = responseBody.data;
        this.lastProduct = this.orders[this.orders.length - 1].cart.products[0].pokemonId;
        this.lastProductIsEmpty = false;
      }
    })
  }

  getAllOrders() {
    this.orderApiService.getAllOrders().subscribe(responseBody => {
      if (responseBody.success == true)
      {
          this.allOrders = responseBody.data;
      }
    })
  }

  getUserObjectCheckForRole() {
    this.sessionApi.checkSession().subscribe(responseBody => {
      this.roleId = responseBody.data.role.roleId;
      if (this.roleId == 1) {
        this.getAllOrdersByUserId(this.userId);
        this.ifAdmin = false;
      } else if (this.roleId == 2) {
        this.getAllOrders();
        this.ifAdmin = true;
      }
    })
  }

  logout(){
    this.sessionApi.logout().subscribe(responseBody => {
      this.router.navigate(['/'])
    })
  }

  goToHome(){
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true) {
        this.router.navigate(['/home'], { queryParams: {id: response.data.userId}}
        )
      }
    });
  }

  goToProfile() {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true) {
        this.router.navigate(['/profile'], { queryParams: {id: response.data.userId}}
        )
      }
    });
  }

  toggleEdit() {
    this.userInfoCom.toggleEdit();
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

}
