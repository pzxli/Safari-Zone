import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  @Input()
  isDarkMode : boolean = false;

  feedbackIsVisible : boolean = false;

  id:number = 0;
  username: string = "";
  password: string = "";
  firstname: string = "";
  lastname: string = "";
  email: string = "";
  shipping: string = "";
  userRoleId : number = 1;

  User:any = {
    id:this.id,
    username: this.username,
    password: this.password,
    firstName: this.firstname,
    lastName: this.lastname,
    email: this.email,
    shippingAddress: this.shipping,
    role: {roleId : this.userRoleId}
  }

  editBox : Boolean = false;

  constructor(private router : Router,  private route : ActivatedRoute, private sessionApi: SessionapiService, private userApi:UserapiService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
    })
    this.getUser();
  }
  getUser(){
    this.userApi.getById(this.id).subscribe(response=>{
      this.User = response;
      this.userRoleId = response.data.role.roleId;
      console.log(response.data.role.roleId)
    })
  }

  edit(){
    this.userApi.getAll().subscribe(users => {
    let User:any = {
      userId: this.id,
      username: this.username,
      password: this.password,
      firstName: this.firstname,
      lastName: this.lastname,
      email: this.email,
      shippingAddress: this.shipping,
      role: {roleId : this.userRoleId}
    }
    let listusers = users.data;
    let unique = true;
    for (let i =0; i < listusers.length; i++)
    {
      if (listusers[i].userId != this.id)
      {
        if (listusers[i].username == User.username)
        {
            unique = false;
            break;
        }
        else if(listusers[i].email == User.email)
        {
            unique = false;
            break;
        }
      }
    }
    if (unique == true)
    {
    const userIsNotEmpty : boolean = Object.values(User).every(x => x != "");

    if (userIsNotEmpty){
        this.userApi.update(User).subscribe(response=>{
          this.userApi.User = User;
          this.toggleEdit();
      })
    }
  }
  else
  {
    this.feedbackIsVisible = true;
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

  goToHistory() {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true) {
        this.router.navigate(['/history'], { queryParams: {id: response.data.userId}}
        )
      }
    });
  }

  toggleEdit() {
    this.editBox = !this.editBox;
    if(this.editBox == false)
    {
        this.id = 0;
        this.username = "";
        this.password = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.shipping = "";
        this.feedbackIsVisible = false;
    }
  }
}
