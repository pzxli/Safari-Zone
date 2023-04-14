package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.Cart;
import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.repositories.CartRepo;
import com.Revature.SafariZoneBackEnd.repositories.ProductRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private CartRepo cartRepo;
    private UserRepo userRepo;
    private ProductRepo productRepo;

    @Autowired
    public CartService(CartRepo cartRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    /**
     * Gets the cart object by Id from the DB
     * @param cartId
     * @return Cart object
     */
    public Optional<Cart> getCartById (Integer cartId) {
        return this.cartRepo.findById(cartId);
    }

    /**
     * Gets all cart objects
     * @param
     * @return List of carts
     */
    public List<Cart> getAll(){
        return this.cartRepo.findAll();
    }

    /**
     * Saves cart object in DB
     * @param cart
     * @return Cart object
     */
    public Cart addCart (Cart cart) {
        Integer userIdFromCart = cart.getUser().getUserId();
        List<Cart> cartsFromDb = cartRepo.findAll();

        if (!cartsFromDb.isEmpty()) {
            for (int i = 0; i < cartsFromDb.size(); i++) {
                if (Objects.equals(userIdFromCart, cartsFromDb.get(i).getUser().getUserId())) {
                    if (!cartsFromDb.get(i).getSubmitted()) {
                        return null;
                    }
                }
            }
        }
        return this.cartRepo.save(cart);
    }

    /**
     * updates cart object in DB
     * @param cart
     * @return Cart object
     */
    public Cart updateCart (Cart cart) {
        cartRepo.save(cart);
        List<PokemonProduct> pokemonProductList = new ArrayList<>();
        List<Integer> productIdsFromDb = this.cartRepo.getAllProductIdsByCartId(cart.getCartId());
        for (int i = 0; i < productIdsFromDb.size(); i++){
            pokemonProductList.add(this.productRepo.findByProductIdAndPurchasedFalse(productIdsFromDb.get(i)));
        }
        if (!pokemonProductList.isEmpty()) {
            cart.setTotal(this.calculateTotal(pokemonProductList));
        } else {
            cart.setTotal(0.00);
        }
        return cartRepo.save(cart);
    }

    /**
     * deletes a cart object in DB
     * @param cartId
     * @returns nothing
     */
    public void deleteCart (Integer cartId) {this.cartRepo.deleteById(cartId);}


    /**
     * finds a users specific cart from the DB
     * @param user
     * @return list of cart objects
     */
    public List<Cart> getCartByUser (User user) {
        return this.cartRepo.getByUser(user);
    }

    /**
     * updates submitted in carts table
     * @param cartId
     * @return 1 or 0 (true or false)
     */
    public Integer submitCart (Integer cartId) {
        return this.cartRepo.setSubmittedFor(cartId);
    }

    /**
     * gets all products in cart
     * @param cartId
     * @return List of product Ids or null
     */
    public List<PokemonProduct> getAllProductsByCartId(Integer cartId) {
        Optional<Cart> cartFromDb = cartRepo.findById(cartId);

        if (cartFromDb.isEmpty()){
            return null;
        }
        List<PokemonProduct> pokemonProductList = new ArrayList<>();
        List<Integer> productIdsFromDb = this.cartRepo.getAllProductIdsByCartId(cartId);
        for (int i = 0; i < productIdsFromDb.size(); i++){
            pokemonProductList.add(this.productRepo.findByProductIdAndPurchasedFalse(productIdsFromDb.get(i)));
        }
        return pokemonProductList;
    }

    /**
     * updates submitted productId to cartId
     * @param cartId
     * @param productId
     * @return nothing
     */
    public void addToCart(Integer cartId, Integer productId) {
        this.cartRepo.SetProductIdToCartId(cartId, productId);
    }

    /**
     * calculate total from the list of pokemonProducts
     * @param pokemonProducts
     * @return sum Total
     */
    public Double calculateTotal(List<PokemonProduct> pokemonProducts){
        List<Double> pokemonProductSumList = new ArrayList<>();
        for (int i = 0; i < pokemonProducts.size(); i++) {
            pokemonProductSumList.add(pokemonProducts.get(i).getProdPrice());
        }
        return pokemonProductSumList.stream().mapToDouble(Double::doubleValue).sum();
    }
}
