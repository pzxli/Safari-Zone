import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';
import 'animate.css';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  isDarkMode : boolean = false;
  darkModeString : string = "Dark Mode";

  quotes : Array<string> = ["You don't turn your back on family. Even when they do.", "There's always room for family.",
    "Money will come and go. We know that. But the most important thing in life will always be the people in this room, right here, right now. Salute, mi familia.",
    "Everyone's looking for the thrill, but what's real is family. Your family.",
    "I don't have friends. I got family.",
    "You never turn your back on family.", 
    "Family is defined by love. Adoption is sharing the love."];
    
  randomIndex : number = Math.floor(Math.random() * this.quotes.length);
  currentQuote : string = "";

  username: string = "";
  password: string = "";
  user:any = {};
  id:number=0;

  feedbackIsVisible : boolean = false;

  constructor (private router : Router,  private route : ActivatedRoute, private sessionApi: SessionapiService, 
    private userApi:UserapiService, private darkModeServ : DarkmodeService) { }

  ngOnInit(): void {
    this.currentQuote = this.quotes[this.randomIndex];
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true){
        this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
      }
    });

    this.isDarkMode = this.darkModeServ.checkDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

  goToRegister(){
    this.router.navigate(['/register']);
  }
  
  login(){
    let user:any = {
      username: this.username,
      password: this.password
    }

    if (user.username != "" && user.password !=""){
      this.sessionApi.login(user).subscribe(response => {
        if (response.success == false){
          this.feedbackIsVisible = true;
        } else {
          this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
        }
      }) 
    }
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }

}