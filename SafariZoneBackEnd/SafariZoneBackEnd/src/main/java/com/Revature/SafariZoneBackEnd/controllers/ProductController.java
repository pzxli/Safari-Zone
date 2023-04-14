package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController
{
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    /**
     * Get all pokemon products
     * @return JsonResponse a list of all products
     */
    @GetMapping
    @ResponseStatus(value=HttpStatus.OK)
    public JsonResponse getAllProducts() {
        List<PokemonProduct> pokemonProductList = productService.getAllProducts();

        if (pokemonProductList.isEmpty()) {
            return new JsonResponse(false, "there are no pokemon products", null);
        }
        return new JsonResponse(true, "listing all pokemon products", pokemonProductList);
    }

    /**
     * Find the pokemon based on Id
     * @param pokeId
     * @param shiny
     * @return JsonResponse either no pokemon found in database or the pokemon's information if Id is valid based on shiny factor
     */
    @GetMapping(value = {"/pokemonId/{pokeId}","/pokemonId/{pokeId}/{shiny}"})
    public JsonResponse getByPokeId(@PathVariable Integer pokeId, @PathVariable Optional<Boolean> shiny)
    {
        List<PokemonProduct> pokemons = this.productService.getByPokemonId(pokeId,shiny);
        if (pokemons.isEmpty()) {
            return new JsonResponse(false,"Pokemons not found",null);
        }
        return new JsonResponse(true, "Pokemon Id:" + pokeId, pokemons);
    }

    /**
     * Find pokemon based on name
     * @param pokeName
     * @param shiny
     * @return JsonResponse either no pokemon found in database or the pokemon's information if name is valid based on shiny factor
     */
    @GetMapping(value = {"/pokemonName/{pokeName}", "/pokemonName/{pokeName}/{shiny}"})
    public JsonResponse getByPokeName(@PathVariable String pokeName, @PathVariable Optional<Boolean> shiny)
    {
        List<PokemonProduct> pokemons = this.productService.getByName(pokeName ,shiny);
        if (pokemons.isEmpty()) {
            return new JsonResponse(false,"Pokemons not found",null);
        }
        return new JsonResponse(true, "Pokemon Name:" + pokeName, pokemons);
    }

    /**
     * Find shiny pokemon
     * @param pokeShiny
     * @return JsonResponse either no pokemon found in database or the pokemon's information if pokemon are shiny
     */
    @GetMapping("/pokemonShiny/{pokeShiny}")
    public JsonResponse getByPokeShiny(@PathVariable Boolean pokeShiny)
    {
        List<PokemonProduct> pokemons = this.productService.getByShiny(pokeShiny);
        if (pokemons.isEmpty())
        {
            return new JsonResponse(false,"Pokemons not found",null);
        }
        return new JsonResponse(true, "Pokemon Shiny:" + pokeShiny, pokemons);
    }

    /**
     * Find pokemon based on their nickname
     * @param pokeNickname
     * @param shiny
     * @return JsonResponse either no pokemon found in database or the pokemon's information if any have a given nickname and is valid based on shiny factor
     */
    @GetMapping(value = {"/pokemonNickname/{pokeNickname}", "/pokemonNickname/{pokeNickname}/{shiny}"})
    public JsonResponse getByPokeNickname(@PathVariable String pokeNickname, @PathVariable Optional<Boolean> shiny)
    {
        List<PokemonProduct> pokemon = this.productService.getByNickName(pokeNickname,shiny);
        if (pokemon.isEmpty())
        {
            return new JsonResponse(false,"Pokemons not found",null);
        }
        return new JsonResponse(true, "Pokemon :" + pokeNickname, pokemon);
    }

    /**
     * Find pokemon based on the productId
     * @param productId
     * @return JsonResponse either no pokemon found in database or the pokemon's information if product Id is valid
     */
    @GetMapping("/productId/{productId}")
    public JsonResponse getByProductId(@PathVariable Integer productId) {
        PokemonProduct pokemon = this.productService.getByProductId(productId);
        if (pokemon == null) {
        return new JsonResponse(false, "Pokemons not found", null);
        }
            return new JsonResponse(true, "Product Id:" + productId, pokemon);
    }

    /**
     * Creating a pokemon product
     * @param pokemonProduct
     * @return JsonResponse submits a new pokemon to be purchased with all information about them
     */
    @PostMapping
    @ResponseStatus(value= HttpStatus.OK)
    public JsonResponse savePokemonProduct(@RequestBody PokemonProduct pokemonProduct) {
        PokemonProduct pokemon = productService.savePokemonProduct(pokemonProduct);
        if (pokemon == null) {
            return new JsonResponse(false, "pokemon product not created", null);
        }
        return new JsonResponse(true, "pokemon product created", pokemon);
    }

    /**
     * Change product to purchased
     * @param productId
     * @return JsonResponse product deleted if exists
     */
    @PutMapping("/productId/{productId}")
    @ResponseStatus(value= HttpStatus.OK)
    public JsonResponse purchaseProduct(@PathVariable Integer productId) {
        this.productService.purchaseProduct(productId);
        return new JsonResponse(true, "product purchased if exists", null);
    }

    /**
     * Variable to find pokemon based on purchased boolean
     * @param purchased
     * @return JsonResponse either no Pokemon not found or Getting all pokemon purchased:
     */
    @GetMapping("/purchased/{purchased}")
    public JsonResponse getByPurchased(@PathVariable Boolean purchased)
    {
        List<PokemonProduct> pokemons = this.productService.getByPurchased(purchased);
        if (pokemons.isEmpty())
        {
            return new JsonResponse(false,"Pokemon not found",null);
        }
        return new JsonResponse(true, "Getting all pokemon purchased:" + purchased, pokemons);
    }
}
