package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.*;
import com.Revature.SafariZoneBackEnd.services.CartService;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {

    CartController cartController;

    CartService cartService = Mockito.mock(CartService.class);
    UserService userService = Mockito.mock(UserService.class);

    public CartControllerTest() {
        this.cartController = new CartController(cartService, userService);
    }

    User user = new User(1, "Test", "User", "TestUser", "Password", "Tester@email.test", "123 ship here", new UserRole());
    Cart cart = new Cart(1, user, new HashSet<>(), 0.00, false);
    Optional<Cart> cartOptional = Optional.of(new Cart(1, user, new HashSet<>(), 0.00, false));
    PokemonProduct exampleProduct = new PokemonProduct(1, 76, "Golem", 15, 300, 99.25d, "Balboa", "Hard exterior, Soft heart", true, false);
    @Test
    void getOneCart() {
        //Arrange
        JsonResponse expectedOutput = new JsonResponse(true, "cart with cartId " + cart.getCartId() + " successfully grabbed", Optional.of(cart));
        Mockito.when(cartService.getCartById((cart.getCartId()))).thenReturn(Optional.ofNullable(cart));

        //Act
        JsonResponse actualOutput = cartController.getOneCart(cart.getCartId());

        //Assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void addCart() {
        //Arrange
        JsonResponse expectedOutput = new JsonResponse(true, "cart successfully created", cart);
        Mockito.when(userService.getOneById(cart.getUser().getUserId())).thenReturn(user);
        Mockito.when(cartService.addCart(cart)).thenReturn(cart);

        //Act
        JsonResponse actualOutput = cartController.addCart(cart);

        //Assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateCart() {
        JsonResponse expectedOutput = new JsonResponse(true, "cart successfully updated", cart);
        Mockito.when(cartService.updateCart(cart)).thenReturn(cart);

        JsonResponse actualOutput = cartController.updateCart(cart);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteCart() {
        JsonResponse expOutput = new JsonResponse(true, "cart with cartId " + cart.getCartId() + " was successfully deleted", null);
        Mockito.when(cartService.getCartById(cart.getCartId())).thenReturn(Optional.ofNullable(cart));

        JsonResponse actualOutput = cartController.deleteCart(cart.getCartId());

        Mockito.verify(cartService).deleteCart(cart.getCartId());
        assertEquals(expOutput, actualOutput);
    }

    @Test
    void submitCart() {
        JsonResponse expOutput = new JsonResponse(true, "cart successfully submitted", cart.getCartId());
        Mockito.when(cartService.submitCart(cart.getCartId())).thenReturn(cart.getCartId());
        Mockito.when(cartService.getCartById(cartOptional.get().getCartId())).thenReturn(cartOptional);

        JsonResponse actualOutput = cartController.submitCart(cart.getCartId());

        assertEquals(expOutput, actualOutput);
    }

    @Test
    void getCartByUsername() {
        //Arrange
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        JsonResponse expOutput = new JsonResponse(true, user.getUsername() + "'s cart successfully grabbed if it isn't submitted", cart);
        Mockito.when(userService.getOneByUsername(user.getUsername())).thenReturn(user);
        System.out.println(user);

        Mockito.when(cartService.getCartByUser(user)).thenReturn(cartList);
        System.out.println(cart);

        //Act
        JsonResponse actualOutput = cartController.getCartByUsername(user.getUsername());

        //Assert
        assertEquals(expOutput, actualOutput);
    }

    @Test
    void getCartByUserId() {
        //Arrange
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        JsonResponse expOutput = new JsonResponse(true, user.getUserId() + "'s cart successfully grabbed if it isn't submitted", cart);
        Mockito.when(userService.getOneById(user.getUserId())).thenReturn(user);
        System.out.println(user);

        Mockito.when(cartService.getCartByUser(user)).thenReturn(cartList);
        System.out.println(cart);

        //Act
        JsonResponse actualOutput = cartController.getCartByUserId(user.getUserId());

        //Assert
        assertEquals(expOutput, actualOutput);
    }

    @Test
    void getAllCart() {
        //Arrange
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        JsonResponse expectedOutput = new JsonResponse(true, "all carts successfully grabbed", carts);
        Mockito.when(cartService.getAll()).thenReturn(carts);

        //Act
        JsonResponse actualOutput = cartController.getAllCart();

        //Assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllProductsByCartId() {
        //Arrange
        List<PokemonProduct> products = new ArrayList<>();
        products.add(exampleProduct);
        JsonResponse expectedOutput = new JsonResponse(true, "displaying all products for cart: " + cart.getCartId(), products);
        Mockito.when(cartService.getCartById(cart.getCartId())).thenReturn(Optional.ofNullable(cart));
        Mockito.when(cartService.getAllProductsByCartId(cart.getCartId())).thenReturn(products);

        //Act
        JsonResponse actualOutput = cartController.getAllProductsByCartId(cart.getCartId());

        //Assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void addProductToCart() {
        //Arrange
        JsonResponse expectedOutput = new JsonResponse(true, "productId: " + exampleProduct.getProductId() + " has been added to cartId: " + cart.getCartId(), null);

        //Act
        JsonResponse actualOutput = cartController.addProductToCart(cart.getCartId(),exampleProduct.getProductId());

        //Assert
        Mockito.verify(cartService).addToCart(cart.getCartId(), exampleProduct.getProductId());
        assertEquals(expectedOutput,actualOutput);
    }
}