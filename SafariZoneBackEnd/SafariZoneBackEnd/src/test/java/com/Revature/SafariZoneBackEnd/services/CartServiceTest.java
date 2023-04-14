package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.Cart;
import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.models.UserRole;
import com.Revature.SafariZoneBackEnd.repositories.CartRepo;
import com.Revature.SafariZoneBackEnd.repositories.ProductRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.util.*;

class CartServiceTest {
    private CartService cartService;
    private CartRepo cartRepo = Mockito.mock(CartRepo.class);
    private UserRepo userRepo = Mockito.mock(UserRepo.class);
    private ProductRepo productRepo = Mockito.mock(ProductRepo.class);


    public CartServiceTest() {
        this.cartService = new CartService(this.cartRepo, this.userRepo, this.productRepo);
    }

    User user = new User(1, "Test", "User", "TestUser", "Password", "Tester@email.test", "123 ship here", new UserRole(1, "customer"));
    PokemonProduct exampleProduct = new PokemonProduct(1, 76, "Golem", 15, 300, 99.25d, "Balboa", "Hard exterior, Soft heart", true, false);
    Set<PokemonProduct> exampleProductSet = new HashSet<PokemonProduct>();
    Cart cart = new Cart(1, user, exampleProductSet, 0.00, false);


    @Test
    void getCartById() {

        //arrange
        Mockito.when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.ofNullable(cart));
        Optional<Cart> expectedOutput = Optional.ofNullable(cart);


        //act
        Optional<Cart> actualOutput = cartService.getCartById(cart.getCartId());

        //assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void addCart() {
        Integer cartId = 2;
        Cart cart2Add = new Cart(cartId, user, new HashSet<>(), 10.00, false);

        cartService.addCart(cart2Add);

        Mockito.verify(this.cartRepo, Mockito.times(1)).save(cart2Add);
    }


    @Test
    void updateCart() {
        //Arrange
        Cart cartToPass = new Cart(1, user, new HashSet<>(), 10.00, false);

        //act
        cartService.updateCart(cartToPass);

        //Assert
        Mockito.verify(this.cartRepo, Mockito.times(2)).save(cartToPass);
    }

    @Test
    void deleteCart() {
        //Arrange
        Integer cartByIdToDelete = 1;
        //Act
        cartService.deleteCart(cartByIdToDelete);
        //Assert
        Mockito.verify(this.cartRepo, Mockito.times(1)).deleteById(cartByIdToDelete);
    }

    @Test
    void getCartByUser() {
        //Arrange
        String username = cart.getUser().getUsername(); //does nothing in test but this is how you get the username
                                                        //inside the cart object
        //Act
        cartService.getCartByUser(user);

        //Assert
        Mockito.verify(this.cartRepo, Mockito.times(1)).getByUser(user);
    }

    @Test
    void submitCart() {
        //Arrange
        Integer cartByIdToSubmit = cart.getCartId();
        //Act
        cartService.submitCart(cartByIdToSubmit);
        //Assert
        Mockito.verify(this.cartRepo, Mockito.times(1)).setSubmittedFor(cartByIdToSubmit);
    }

    @Test
    void getAllProductsInCart() {

        //arrange

        List<Integer> productIdsFromCart = new ArrayList<>();
        productIdsFromCart.add(exampleProduct.getProductId());
        Mockito.when(cartRepo.findById(cart.getCartId())).thenReturn(Optional.ofNullable(cart));
        Mockito.when(cartRepo.getAllProductIdsByCartId(cart.getCartId())).thenReturn(productIdsFromCart);
        Mockito.when(productRepo.findByProductIdAndPurchasedFalse(exampleProduct.getProductId())).thenReturn(exampleProduct);
        List<PokemonProduct> expectedOutput = new ArrayList<>();
        expectedOutput.add(exampleProduct);
        Mockito.when(cartRepo.getAllProductIdsByCartId(cart.getCartId())).thenReturn(productIdsFromCart);

        //act

        List<PokemonProduct> actualOutput = cartService.getAllProductsByCartId(cart.getCartId());

        //assert
        
        Assertions.assertEquals(expectedOutput, actualOutput);

    }
}