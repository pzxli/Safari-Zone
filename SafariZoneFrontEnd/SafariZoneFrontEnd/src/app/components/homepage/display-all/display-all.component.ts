import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ProductapiService } from 'src/app/services/productapi.service';
import { CreateProductComponent } from '../create-product/create-product.component';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';
import { ProductCardComponent } from '../product-card/product-card.component';
import { SearchComponent } from '../search/search.component';
import { DarkmodeService } from 'src/app/services/darkmode.service';

@Component({
  selector: 'app-display-all',
  templateUrl: './display-all.component.html',
  styleUrls: ['./display-all.component.css']
})
export class DisplayAllComponent implements OnInit {

  
  isDarkMode : boolean = false;
  darkModeString : string = "";
  canCreate : boolean = false;

  @ViewChild(SearchComponent)
  private searchProductCom!: SearchComponent;

  @ViewChild(CreateProductComponent)
  private createProductCom!: CreateProductComponent;

  constructor(private router : Router,  private sessionApi: SessionapiService, private userApi:UserapiService,
    public productApi : ProductapiService, private darkModeServ : DarkmodeService) { }

  ngOnInit(): void {
    this.isDarkMode = this.darkModeServ.checkDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
    this.sessionApi.checkSession().subscribe(user =>{
      if (user.success == true)
      {
        if (user.data.role.roleId == 2)
        {
          this.canCreate = true;
        }
      }
    })
  }

  toggleCreate() {
    this.clearSearch();
    this.createProductCom.toggleCreate();
  }

  logout(){
    this.sessionApi.logout().subscribe(responseBody => {
      this.router.navigate(['/'])
    })
    
  }

  toggleSearch() {
    this.searchProductCom.showSearch();
  }

  clearSearch() {
    this.searchProductCom.resetSearch();
  }

  goToHistory() {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true) {
        this.router.navigate(['/history'], { queryParams: {id: response.data.userId}}
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
    })
  }

  toggleDarkMode(){
    this.isDarkMode = this.darkModeServ.toggleDarkMode();
    this.darkModeString = this.darkModeServ.checkDarkMode() == true ? "Normal" : "Dark";
  }
}
