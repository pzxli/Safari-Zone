package com.Revature.SafariZoneBackEnd.repositories;

import com.Revature.SafariZoneBackEnd.models.Cart;
import com.Revature.SafariZoneBackEnd.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> getByUser(User user);

    @Modifying
    @Query("update Cart set submitted = true where cartId = ?1")
    Integer setSubmittedFor(Integer cartId);

    @Modifying
    @Query(value = "SELECT * FROM CARTS WHERE SUBMITTED = FALSE",
    nativeQuery = true)
    List<Cart> getAllUnSubmitted();


    @Modifying
    @Query(value = "SELECT product_id FROM PRODUCTS P WHERE CART_ID = ?1",
    nativeQuery = true)
    List<Integer> getAllProductIdsByCartId(Integer cartId);

    @Modifying
    @Query(value = "INSERT INTO PRODUCTS (cart_id, product_id) VALUES (?1, ?2)",
            nativeQuery = true)
    void SetProductIdToCartId(Integer cartId, Integer productId);

}
