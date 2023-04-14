package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.Cart;
import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.services.CartService;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cart")
public class CartController {
    private CartService cartService;
    private UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    /**
     * Get all cart content or return null
     * @return JsonResponse no carts available or all carts successfully grabbed
     */
    @GetMapping("all")
    public JsonResponse getAllCart() {
        JsonResponse jsonResponse;
        List<Cart> cartListFromDb = cartService.getAll();

        if (cartListFromDb == null) {
            return jsonResponse = new JsonResponse(false, "no carts available", null);
        }
        return jsonResponse = new JsonResponse(true, "all carts successfully grabbed", cartListFromDb);
    }

    /**
     * Get one cartId
     * @param cartId
     * @return JsonResponse cart with cartId does not exist or cart with cartId successfully grabbed
     */
    @GetMapping("id/{cartId}")
    public JsonResponse getOneCart(@PathVariable Integer cartId) {
        JsonResponse jsonResponse;
        Optional<Cart> cartFromDb = cartService.getCartById(cartId);
        if (cartFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "cart with cartId " + cartId + " does not exist", null);
        }
        return jsonResponse = new JsonResponse(true, "cart with cartId " + cartId + " successfully grabbed", cartFromDb);
    }

    /**
     * Add to Cart
     * @param cart
     * @return JsonResponse user does not exist or cart already exists or cart successfully created
     */
    @PostMapping
    public JsonResponse addCart(@RequestBody Cart cart) {
        JsonResponse jsonResponse;
        User userFromDb = userService.getOneById(cart.getUser().getUserId());
        if (userFromDb == null) {
            return jsonResponse = new JsonResponse(false, "user does not exist", null);
        }
        Cart cartFromDb = cartService.addCart(cart);
        if (cartFromDb == null) {
            return jsonResponse = new JsonResponse(false, "cart already exists for user", null);
        }
        return jsonResponse = new JsonResponse(true, "cart successfully created", cartFromDb);
    }

    /**
     * Update Cart content
     * @param cart
     * @return JsonResponse cart not found or cart successfully updated
     */
    @PutMapping
    public JsonResponse updateCart(@RequestBody Cart cart) {
        JsonResponse jsonResponse;
        Cart cartFromDb = cartService.updateCart(cart);

        if (cartFromDb == null) {
            return new JsonResponse(false, "cart not found", null);
        }

        return new JsonResponse(true, "cart successfully updated", cartFromDb);
    }

    /**
     * Delete Cart content
     * @param cartId
     * @return JsonResponse no cart with cartId or cart with cartId was successfully deleted
     */
    @DeleteMapping("{cartId}")
    public JsonResponse deleteCart(@PathVariable Integer cartId) {
        JsonResponse jsonResponse;
        Optional<Cart> cartFromDb = cartService.getCartById(cartId);

        if (cartFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "no cart with cartId " + cartId + " was found", null);
        }
        cartService.deleteCart(cartId);
        return jsonResponse = new JsonResponse(true, "cart with cartId " + cartId + " was successfully deleted", null);
    }

    /**
     * Submits Cart content
     * @param cartId
     * @return JsonResponse cart with Id: does not exist or cart with Id: has already benn submitted or cart successfully submitted
     */
    @PutMapping("{cartId}")
    public JsonResponse submitCart(@PathVariable Integer cartId) {
        JsonResponse jsonResponse;
        Optional<Cart> optionalCartFromDb = cartService.getCartById(cartId);
        if (optionalCartFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "cart with Id: " + cartId + " does not exist", null);
        }
        Cart cartFromDb = optionalCartFromDb.get();
        if (cartFromDb.getSubmitted()) {
            return jsonResponse = new JsonResponse(false, "cart with Id :" + cartId + " has already been submitted", null);
        }

        Integer someNumber = cartService.submitCart(cartId);
        return jsonResponse = new JsonResponse(true, "cart successfully submitted", someNumber);
    }

    /**
     * Get cart by username
     * @param username
     * @return JsonResponse username is not in database or cart for user does not exist
     * or username's cart successfully grabbed if it isn't submitted or cart for user: has been submitted
     */
    @GetMapping("username/{username}")
    public JsonResponse getCartByUsername(@PathVariable String username) {
        User userFromDb = userService.getOneByUsername(username);
        if (userFromDb == null) {
            return new JsonResponse(false, "username is not in database", null);
        } else {
            List<Cart> cartListFromDb = cartService.getCartByUser(userFromDb);

            if (cartListFromDb.isEmpty()) {
                return new JsonResponse(false, "cart for user " + username + " does not exist", null);
            }

            for (int i = 0; i < cartListFromDb.size(); i++) {
                if (!cartListFromDb.get(i).getSubmitted()) {
                    Cart cartFromDb = cartListFromDb.get(i);
                    return new JsonResponse(true, username + "'s cart successfully grabbed if it isn't submitted", cartFromDb);
                }
            }

            return new JsonResponse(false, "cart for user: " + username + " has been submitted", null);

        }
    }

    /**
     * Looking up one cart by ID
     * @param userId
     * @return JsonResponse userId is not in database or cart for user does not exist
     * or userId's cart successfully grabbed if it isn't submitted or cart for user: has been submitted
     */
    @GetMapping("userId/{userId}")
    public JsonResponse getCartByUserId(@PathVariable Integer userId) {

        User userFromDb = userService.getOneById(userId);
        if (userFromDb == null) {
            return new JsonResponse(false, "userId is not in database", null);
        } else {
            List<Cart> cartListFromDb = cartService.getCartByUser(userFromDb);

            if (cartListFromDb.isEmpty()) {
                return new JsonResponse(false, "cart for user " + userId + " does not exist", null);
            }

            for (int i = 0; i < cartListFromDb.size(); i++) {
                if (!cartListFromDb.get(i).getSubmitted()) {
                    Cart cartFromDb = cartListFromDb.get(i);
                    return new JsonResponse(true, userId + "'s cart successfully grabbed if it isn't submitted", cartFromDb);
                }
            }

            return new JsonResponse(false, "cart for user: " + userId + " has been submitted", null);

        }
    }

    /**
     * Getting all carts
     * @param cartId
     * @return JsonResponse cart does not exist or not products in cart or displaying all products for cart:
     */
    @GetMapping("allproducts/{cartId}")
    public JsonResponse getAllProductsByCartId (@PathVariable Integer cartId) {
        JsonResponse jsonResponse;
        Optional<Cart> cartFromDb = cartService.getCartById(cartId);
        if(cartFromDb.isEmpty()){
            return jsonResponse = new JsonResponse(false, "cart does not exist", null);
        }
        List<PokemonProduct> productsFromDb = cartService.getAllProductsByCartId(cartId);
        if(productsFromDb.isEmpty()){
            return jsonResponse = new JsonResponse(false, "no products in cart", null);
        }
        return jsonResponse = new JsonResponse(true, "displaying all products for cart: " + cartId, productsFromDb);
    }

    /**
     * Add product to cart
     * @param cartId
     * @param productId
     * @return JsonResponse productId: has been added to cart
     */
    @PostMapping("addtocart/{cartId}/{productId}")
    public JsonResponse addProductToCart (@PathVariable Integer cartId, @PathVariable Integer productId) {
        JsonResponse jsonResponse;
        cartService.addToCart(cartId, productId);
        return jsonResponse = new JsonResponse(true, "productId: " + productId + " has been added to cartId: " + cartId, null);
    }
}
