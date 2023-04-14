package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductControllerTest {

    ProductController productController;
    ProductService productService = Mockito.mock(ProductService.class);

    public ProductControllerTest(){
        this.productController = new ProductController(productService);
    }

    PokemonProduct pokemonProduct = new PokemonProduct(1, 1, "Snorlax", 1, 460, 100.00,"Loaf", "Big Sleep", false, false);

    PokemonProduct shinyPokemon = new PokemonProduct(1, 1, "Snorlax", 1, 460, 100.00,"Loaf", "Big Sleep", true, false);

    PokemonProduct purchasedPokemon = new PokemonProduct(1, 1, "Snorlax", 1, 460, 100.00,"Loaf", "Big Sleep", true, true);

    @Test
    void getByPokeIdPass() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(true, "Pokemon Id:" + 1, pokemonProducts);
        Mockito.when(productService.getByPokemonId(pokemonProduct.getPokemonId(),null)).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeId(1,null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeIdFail() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(false,"Pokemons not found",null);
        Mockito.when(productService.getByPokemonId(pokemonProduct.getPokemonId(),null)).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeId(2,null);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getByPokeNamePass() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(true, "Pokemon Name:" + pokemonProduct.getPokemonName(), pokemonProducts);
        Mockito.when(productService.getByName(pokemonProduct.getPokemonName(),null)).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeName("Snorlax",null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeNameFail() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(false,"Pokemons not found",null);
        Mockito.when(productService.getByName(pokemonProduct.getPokemonName(),null)).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeName("Mewtwo",null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeShinyPass() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(shinyPokemon);
        JsonResponse expectedOutput = new JsonResponse(true, "Pokemon Shiny:" + shinyPokemon.getShiny(), pokemonProducts);
        Mockito.when(productService.getByShiny(shinyPokemon.getShiny())).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeShiny(true);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeShinyFail() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(false, "Pokemons not found", null);
        Mockito.when(productService.getByShiny(pokemonProduct.getShiny())).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPokeShiny(true);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeNicknamePass() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(true, "Pokemon :" + pokemonProduct.getNickname(), pokemonProducts);
        Mockito.when(productService.getByNickName(pokemonProduct.getNickname(),null)).thenReturn(pokemonProducts);


        //act
        JsonResponse actualOutput = productController.getByPokeNickname("Loaf",null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByPokeNicknameFail() {

        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(pokemonProduct);
        JsonResponse expectedOutput = new JsonResponse(false, "Pokemons not found", null);
        Mockito.when(productService.getByNickName(pokemonProduct.getNickname(),null)).thenReturn(pokemonProducts);


        //act
        JsonResponse actualOutput = productController.getByPokeNickname("Rock",null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByProductIdPass() {

        //arrange
        PokemonProduct productId = pokemonProduct;
        JsonResponse expectedOutput = new JsonResponse(true, "Product Id:" + pokemonProduct.getProductId(), pokemonProduct);
        Mockito.when(productService.getByProductId(pokemonProduct.getProductId())).thenReturn(productId);

        //act
        JsonResponse actualOutput = productController.getByProductId(1);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getByProductIdFail() {

        //arrange
        PokemonProduct productId = pokemonProduct;
        JsonResponse expectedOutput = new JsonResponse(false, "Pokemons not found", null);
        Mockito.when(productService.getByProductId(pokemonProduct.getProductId())).thenReturn(productId);

        //act
        JsonResponse actualOutput = productController.getByPokeId(1, null);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void savePokemonProductPass() {

        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "pokemon product created", pokemonProduct);
        Mockito.when(productService.savePokemonProduct(pokemonProduct)).thenReturn(pokemonProduct);

        //act
        JsonResponse actualOutput = productController.savePokemonProduct(pokemonProduct);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void savePokemonProductFail() {

        //arrange
        PokemonProduct pokemonProduct = new PokemonProduct(1,null,null,null,null,null,null,null,null,null);
        JsonResponse expectedOutput = new JsonResponse(false, "pokemon product not created", null);
        Mockito.when(productService.savePokemonProduct(pokemonProduct)).thenReturn(null);

        //act
        JsonResponse actualOutput = productController.savePokemonProduct(pokemonProduct);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void purchaseProduct() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "product purchased if exists", null);
        Mockito.when(productService.purchaseProduct(pokemonProduct.getProductId())).thenReturn(pokemonProduct.getProductId());

        //act
        JsonResponse actualOutput = productController.purchaseProduct(pokemonProduct.getProductId());

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getByPurchased() {
        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(purchasedPokemon);
        JsonResponse expectedOutput = new JsonResponse(true, "Getting all pokemon purchased:" + purchasedPokemon.getPurchased(), pokemonProducts);
        Mockito.when(productService.getByPurchased(purchasedPokemon.getPurchased())).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getByPurchased(true);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllProducts() {
        //arrange
        List<PokemonProduct> pokemonProducts = new ArrayList<>();
        pokemonProducts.add(purchasedPokemon);
        JsonResponse expectedOutput = new JsonResponse(true, "listing all pokemon products", pokemonProducts);
        Mockito.when(productService.getAllProducts()).thenReturn(pokemonProducts);

        //act
        JsonResponse actualOutput = productController.getAllProducts();

        //assert
        assertEquals(expectedOutput, actualOutput);
    }
}
