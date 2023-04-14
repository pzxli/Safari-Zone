import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  isDarkMode : boolean = false;
  darkModeString :string = "";
  feedbackIsVisible : boolean = false;

  username: string = "";
  password: string = "";
  firstname: string = "";
  lastname: string = "";
  email: string = "";
  shipping: string = "";

  User:any = {
    username: this.username,
    password: this.password,
    firstName: this.firstname,
    lastName: this.lastname,
    email: this.email,
    shippingAddress: this.shipping,
    role: {roleId : 1}
  }

  constructor(private router : Router,private route : ActivatedRoute, private apiUser: UserapiService, 
    private sessionApi: SessionapiService, private darkModeServ : DarkmodeService ) { }

  ngOnInit(): void {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true){
        this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
      }
    });

    this.isDarkMode = this.darkModeServ.checkDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

  register(){
    let User:any = {
      username: this.username,
      password: this.password,
      firstName: this.firstname,
      lastName: this.lastname,
      email: this.email,
      shippingAddress: this.shipping,
      role: {roleId : 1}
    }
    
    const userIsNotEmpty : boolean = Object.values(User).every(x => x != "");

    if (userIsNotEmpty){
      this.apiUser.create(User).subscribe(response=>{
        if(response.success == true){
          this.router.navigate([""]);
        } else{
          this.feedbackIsVisible = true;
        }
      })
    }
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

}
