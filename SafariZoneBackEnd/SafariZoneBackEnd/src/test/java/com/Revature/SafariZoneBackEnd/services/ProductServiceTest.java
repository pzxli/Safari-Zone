package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    private ProductRepo productRepo = Mockito.mock(ProductRepo.class);

    public ProductServiceTest(){
        this.productService = new ProductService(productRepo);
    }

    PokemonProduct defaultPokemonProduct = new PokemonProduct(1, 76, "Golem", 15, 300, 99.25d, "Balboa", "Hard exterior, Soft heart", true, false);

    @Test
    void getByProductIdValidId() {
        //arrange
        int productId = defaultPokemonProduct.getProductId();
        PokemonProduct expectedOutput = defaultPokemonProduct;
        Mockito.when(productRepo.findByProductIdAndPurchasedFalse(productId)).thenReturn(expectedOutput);
        //act
        PokemonProduct actualOutput = productService.getByProductId(productId);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getByProductIdInvalidId() {
        //arrange
        int productId = defaultPokemonProduct.getProductId();
        PokemonProduct expectedOutput = defaultPokemonProduct;
        Mockito.when(productRepo.findByProductIdAndPurchasedFalse(productId)).thenReturn(expectedOutput);
        //act
        PokemonProduct actualOutput = productService.getByProductId(7);
        //assert
        assertNotEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllProducts() {
        //arrange
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);
        expectedOutput.add(new PokemonProduct(2, 94, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false));

        Mockito.when(productRepo.findAll()).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getAllProducts();
        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getByNameValidName() {
        //arrange
        String pokemonName = defaultPokemonProduct.getPokemonName();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByPokemonNameAndPurchasedFalse(pokemonName)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByName(pokemonName,Optional.of(true));
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByNameInvalidName() {
        //arrange
        String pokemonName = defaultPokemonProduct.getPokemonName();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByPokemonNameAndPurchasedFalse(pokemonName)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByName("cat", Optional.of(false));
        //assert
        assertNotEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByShinyValid() {
        //arrange
        Boolean isShiny = true;
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);
        Mockito.when(productRepo.findByShinyAndPurchasedFalse(isShiny)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByShiny(isShiny);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByShinyInvalid() {
        //arrange
        Boolean isShiny = true;
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);
        Mockito.when(productRepo.findByShinyAndPurchasedFalse(isShiny)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByShiny(false);
        //assert
        assertNotEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByNickNameValid() {
        //arrange
        String nickname = defaultPokemonProduct.getNickname();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByNicknameNative(nickname)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByNickName(nickname,Optional.of(true));
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByNickNameInvalid() {
        //arrange
        String nickname = defaultPokemonProduct.getNickname();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByNicknameNative(nickname)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByNickName("cat",Optional.of(false));
        //assert
        assertNotEquals(expectedOutput, actualOutput);
    }

    @Test
    void getByPokemonIdValid() {
        //arrange
        Integer pokemonId = defaultPokemonProduct.getPokemonId();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByPokemonIdAndPurchasedFalse(pokemonId)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByPokemonId(pokemonId, Optional.of(true));
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void getByPokemonIdInvalid() {
        //arrange
        Integer pokemonId = defaultPokemonProduct.getPokemonId();
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);

        Mockito.when(productRepo.findByPokemonIdAndPurchasedFalse(pokemonId)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByPokemonId(84678, Optional.of(false));
        //assert
        assertNotEquals(expectedOutput, actualOutput);
    }


    @Test
    void savePokemonProductValid() {
        //arrange
        PokemonProduct gengarPokemonProduct = new PokemonProduct(2, 94, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false);
        PokemonProduct expectedOutput = gengarPokemonProduct;
        Mockito.when(productRepo.save(gengarPokemonProduct)).thenReturn(expectedOutput);
        //act
        PokemonProduct actualOutput = productService.savePokemonProduct(gengarPokemonProduct);
        //assert
        assertEquals(expectedOutput,actualOutput);
    }
    @Test
    void savePokemonProductInvalid() {
        //arrange
        PokemonProduct gengarPokemonProduct = new PokemonProduct(2, 94, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false);
        PokemonProduct expectedOutput = gengarPokemonProduct;
        Mockito.when(productRepo.save(gengarPokemonProduct)).thenReturn(expectedOutput);
        //act
        PokemonProduct actualOutput = productService.savePokemonProduct( new PokemonProduct(2, 9400, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false));

        //assert
        assertNotEquals(expectedOutput,actualOutput);
    }

    @Test
    void deletePokemonProduct() {
        //arrange
        PokemonProduct gengarPokemonProduct = new PokemonProduct(2, 94, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false);

        //act
        productService.deletePokemonProduct(2);
        //assert
        Mockito.verify(productRepo,Mockito.times(1)).deleteById(2);
    }

    @Test
    void purchaseProduct() {
        //arrange
        PokemonProduct gengarPokemonProduct = new PokemonProduct(2, 94, "Gengar", 15, 40, 125.01d, "Paul Blart", "Big Smile", false, false);
        //act
        productService.purchaseProduct(2);
        //assert
        Mockito.verify(productRepo,Mockito.times(1)).setPurchasedFor(2);
    }

    @Test
    void getByPurchased() {
        //arrange
        Boolean purchased = true;
        List<PokemonProduct> expectedOutput = new ArrayList<PokemonProduct>();
        expectedOutput.add(defaultPokemonProduct);
        Mockito.when(productRepo.findByPurchased(purchased)).thenReturn(expectedOutput);
        //act
        List<PokemonProduct> actualOutput = productService.getByPurchased(purchased);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
}