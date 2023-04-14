import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Pokemon } from '../components/models/Pokemon';
import { Product } from '../components/models/Product';

const baseUrl = `${environment.domain}/product`;
@Injectable({
  providedIn: 'root'
})
export class ProductapiService {

  name : string = "pikachu"
  id : number = 1
  allProducts : Array<any> = [];
  isSearching : boolean = false;
  productsInCart : Array<number> = [];

  constructor(private conn : HttpClient) {
   }

   getAllPokemon(){
     return this.conn.get<any>("https://pokeapi.co/api/v2/pokemon?limit=151")
   }

   getOnePokemonByName(){
     return this.conn.get<Pokemon>(`https://pokeapi.co/api/pokemon${this.name}`)
   }

   getOnePokemonById(id : string){
    return this.conn.get<Pokemon>(`https://pokeapi.co/api/v2/pokemon/${id}`)
   }

   getAllProducts(){
    return this.conn.get<any>(baseUrl,{
      withCredentials : true
    });
   }

   getAllAvailableProducts() {
     return this.conn.get<any>(`${baseUrl}/purchased/false`, {
       withCredentials : true
     });
   }

   getByPokeID(pokeId : number, shiny? : boolean){
    if(shiny != undefined){
      return this.conn.get<any>(`${baseUrl}/pokemonId/${pokeId}/${shiny}`, {
        withCredentials: true
      });
    }

    return this.conn.get<any>(`${baseUrl}/pokemonId/${pokeId}`,{
      withCredentials : true
    });
   }

   getByPokeName(pokeName : String, shiny? : boolean){
    if(shiny != undefined){
      return this.conn.get<any>(`${baseUrl}/pokemonName/${pokeName}/${shiny}`, {
        withCredentials: true
      });
    }

    return this.conn.get<any>(`${baseUrl}/pokemonName/${pokeName}`,{
      withCredentials : true
    });
   }

   getByPokeShiny(pokeShiny : boolean){
    return this.conn.get<any>(`${baseUrl}/pokemonShiny/${pokeShiny}`,{
      withCredentials : true
    });
   }

   getByPokeNickname(pokeNickname : String, shiny? : boolean){
    if(shiny != undefined){
      return this.conn.get<any>(`${baseUrl}/pokemonNickname/${pokeNickname}/${shiny}`, {
        withCredentials: true
      });
    }

    return this.conn.get<any>(`${baseUrl}/pokemonNickname/${pokeNickname}`,{
      withCredentials : true
    });
   }

   getByProductId(productId : number){
    return this.conn.get<any>(`${baseUrl}/productId/${productId}`,{
      withCredentials : true
    });
   }

   saveProduct(product : Product){
    return this.conn.post<any>(`${baseUrl}`, product, {
      withCredentials : true
    });
   }

   deleteProduct(productId : number){
    return this.conn.delete<any>(`${baseUrl}/${productId}`,{
      withCredentials : true
    });
   }

   purchaseProduct(productId : number) {
     return this.conn.put<any>(`${baseUrl}/productId/${productId}`,{
      withCredentials : true
    });
   }

}

