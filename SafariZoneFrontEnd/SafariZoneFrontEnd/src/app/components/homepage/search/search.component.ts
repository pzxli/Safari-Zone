import { Component, Input, OnInit } from '@angular/core';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { ProductapiService } from 'src/app/services/productapi.service';
import { ProductCardComponent } from '../product-card/product-card.component';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private productService : ProductapiService, private darkModeServ : DarkmodeService) { }

  @Input()
  isDarkMode : boolean = false;

  searchType : number = 0;
  shinySearch : boolean = false;
  isShiny : boolean | null = null;

  productId : number = 1;
  pokeId : number = 1;
  pokeName : string = "";
  pokeNickname : string = "";

  toggleSearch : boolean = false;

  updateMessageVariable : string = "";

  ngOnInit(): void {
    this.isDarkMode = this.darkModeServ.checkDarkMode();
  }

  checkbox(){
    this.isShiny = null;
  }
  
  search(){
    if(this.searchType == 1){
      if(this.productId === null || this.productId < 1){
        this.updateMessageVariable = "Please enter a valid Product ID";
      }
      else{
      this.productService.getByProductId(this.productId).subscribe(responseBody => {
        if(responseBody.success == false){
          this.updateMessageVariable = "No results found, please try again";
        }else{
        this.productService.allProducts = [];
        this.productService.allProducts.push(responseBody.data) 
        this.showSearch(); 
        this.productService.isSearching = true; 
      }
      });      
      }
    }

    if(this.searchType == 2){
      if(this.pokeId == null || this.pokeId < 1){
        this.updateMessageVariable = "Please enter a valid Pokémon ID";
      }else{
        if(this.isShiny != null) {
          this.productService.getByPokeID(this.pokeId, this.isShiny).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
            this.productService.allProducts = responseBody.data;
            this.showSearch();
            this.productService.isSearching = true;
            }
          });
      } else{
        this.productService.getByPokeID(this.pokeId).subscribe(responseBody => {
          if(responseBody.success == false){
            this.updateMessageVariable = "No results found, please try again";
          }else{
          this.productService.allProducts = responseBody.data;
          this.showSearch();
          this.productService.isSearching = true;
          }
        });
        }
      }
    }

    if(this.searchType == 3){
      if(this.pokeName === ""){
        this.updateMessageVariable = "Please enter a valid Pokémon name";
      }else{
        if(this.isShiny != null) {
          this.productService.getByPokeName(this.pokeName, this.isShiny).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
              this.productService.allProducts = responseBody.data;
              this.showSearch();
              this.productService.isSearching = true;
            }
        });
        }else{
          this.productService.getByPokeName(this.pokeName).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
              this.productService.allProducts = responseBody.data;
              this.showSearch();
              this.productService.isSearching = true;
            }
        });
        }     
      }
    }

    if(this.searchType == 4){
      if(this.pokeNickname === ""){
        this.updateMessageVariable = "Please enter a valid Pokémon nickname";
      }else{
        if(this.isShiny != null) {
          this.productService.getByPokeNickname(this.pokeNickname, this.isShiny).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
              this.productService.allProducts = responseBody.data;
              this.showSearch();
              this.productService.isSearching = true;
            }
        });
        } else{
          this.productService.getByPokeNickname(this.pokeNickname).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
              this.productService.allProducts = responseBody.data;
              this.showSearch();
              this.productService.isSearching = true;
            }
          });
        }   
      }
    }

    if(this.searchType == 5){
      if(this.isShiny === null){
        this.updateMessageVariable = "Please select an option";
      }else{
        if(this.isShiny != null){
          this.productService.getByPokeShiny(this.isShiny).subscribe(responseBody => {
            if(responseBody.success == false){
              this.updateMessageVariable = "No results found, please try again";
            }else{
                this.productService.allProducts = responseBody.data;
                this.showSearch();
                this.productService.isSearching = true;
            }
          });
        }
      }
    }
  }

  showSearch(){
    this.productId = 1;
    this.pokeId = 1;
    this.pokeName = ``;
    this.pokeNickname = ``;
    this.updateMessageVariable = "";
    this.shinySearch = false;
    this.searchType = 0;
    this.toggleSearch = !this.toggleSearch;
  }

  emptyCheckbox(){
    this.shinySearch = false;
    this.isShiny = null;
    this.updateMessageVariable = "";
  }

  resetSearch(){
    this.productService.isSearching = false;
    this.productService.getAllAvailableProducts().subscribe(responseBody =>{
      this.productService.allProducts = responseBody.data;
    });
  }
}
