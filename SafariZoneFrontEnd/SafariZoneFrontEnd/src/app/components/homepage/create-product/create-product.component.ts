import { Component, DoCheck, Input, OnInit, ViewChild } from '@angular/core';
import { DarkmodeService } from 'src/app/services/darkmode.service';
import { ProductapiService } from 'src/app/services/productapi.service';
import { Pokemon } from '../../models/Pokemon';
import { Product } from '../../models/Product';
import { ProductCardComponent } from '../product-card/product-card.component';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit, DoCheck{

  @Input()
  isDarkMode : boolean = false;
  pokemonList : Array<any> = [];
  pokemon : Pokemon = <Pokemon>{};
  sprite : string = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png";
  height : number = 7;
  weight : number = 69;
  product : Product = <Product>{};
  productList : Array<Product> = [];
  createBox : Boolean = false;
  updateMessageVariable : string = "";

  // filtering
  selectedPokemon : any;
  filterInput : string = "";
  filteredList : Array<any> = [];

  // defaults
  defaultPokemon = 1;
  productShiny = false;

  constructor(private productApiService : ProductapiService, private darkModeServ : DarkmodeService) { }


  ngOnInit(): void {
    this.productList = this.productApiService.allProducts;
    this.getAllPokemon();
    this.isDarkMode = this.darkModeServ.checkDarkMode();
  }

  ngDoCheck(): void {
    this.filteredList = this.pokemonList.filter((pokemon : any) => pokemon.name.includes(this.filterInput.toLowerCase()));
  }

  getAllPokemon() {
    this.productApiService.getAllPokemon().subscribe(responseBody => {
      this.pokemonList = responseBody.results;
      this.filteredList = this.pokemonList;
    })
  }

  getOnePokemonById(createDropdown : string) {
    this.productApiService.getOnePokemonById(createDropdown).subscribe(responseBody => {
      this.pokemon = responseBody;
      this.sprite = responseBody.sprites.front_default;
      this.height = responseBody.height;
      this.weight = responseBody.weight;
    })
  }

  createProduct(pokemonId : any) {
    this.productApiService.getOnePokemonById(pokemonId).subscribe(pokemonToPost => {
      this.pokemon = pokemonToPost;
      this.sprite = pokemonToPost.sprites.front_default;
      this.height = (pokemonToPost.height);
      this.weight = (pokemonToPost.weight);

      this.product.pokemonId = pokemonToPost.id;
      this.product.pokemonName = pokemonToPost.name;
      this.product.pokeSpriteUrl = this.sprite;
      this.product.shiny = this.productShiny;

      if (this.product.nickname == null && this.product.prodHeight == null && this.product.prodWeight == null && this.product.prodPrice == null){
        this.updateMessageVariable = "Please fill out all fields"
      }
      else if (this.product.nickname == null || this.product.nickname == "")
      {
        this.updateMessageVariable = "Please give the Pokémon a nickname";
      }
      else if (this.product.prodHeight == null)
      {
          this.updateMessageVariable = "Please give the Pokémon a height";
      }
      else if (this.product.prodWeight == null)
      {
          this.updateMessageVariable = "Please give the Pokémon a weight";
      }
      else if (this.product.prodPrice == null)
      {
        this.updateMessageVariable = "Please input a price value";
      }
      else{
      this.productApiService.saveProduct(this.product).subscribe(responseBody => {
        
        this.productApiService.allProducts.push(responseBody.data);
        this.productList = this.productApiService.allProducts;
        this.toggleCreate();
      })
      }
    })
  }

  toggleCreate() {
    this.updateMessageVariable = "";
    this.defaultPokemon = 1;
    this.product.prodPrice = null as any;
    this.product.nickname = null as any;
    this.product.prodWeight = null as any;
    this.product.prodHeight = null as any;
    this.product.description = "";
    this.productShiny = false;
    this.createBox = !this.createBox;
    this.product.purchased = false;
  }

}
