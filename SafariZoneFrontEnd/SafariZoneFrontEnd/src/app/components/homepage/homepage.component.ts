import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionapiService } from 'src/app/services/sessionapi.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  id:number=0;

  constructor(private router : Router,  private route : ActivatedRoute, private sessionApi: SessionapiService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
    })
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == false){
        this.router.navigate(['']);
      }
      else if(response.data.userId != this.id)
      {
        this.router.navigate(['/home'], { queryParams: {id:response.data.userId}})
      }
    });
  }
  
  logout(){
    this.sessionApi.logout().subscribe(responseBody => {
    })
  }

}
