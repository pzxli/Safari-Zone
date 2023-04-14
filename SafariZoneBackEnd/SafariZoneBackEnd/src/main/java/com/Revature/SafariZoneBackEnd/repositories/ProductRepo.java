package com.Revature.SafariZoneBackEnd.repositories;

import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<PokemonProduct, Integer>
{
    List<PokemonProduct> findByPokemonIdAndPurchasedFalse(Integer id);

    @Query(value = "SELECT * FROM pokemon_product WHERE purchased = FALSE AND pokemon_name iLIKE ?1%", nativeQuery = true)
    List<PokemonProduct> findByPokemonNameAndPurchasedFalse(String name);

    List<PokemonProduct> findByShinyAndPurchasedFalse(Boolean shiny);

    @Query(value = "SELECT * FROM pokemon_product WHERE purchased = FALSE AND nickname iLIKE ?1%", nativeQuery = true)
    List<PokemonProduct> findByNicknameNative(String nickname);

    PokemonProduct findByProductIdAndPurchasedFalse(Integer productId);

    @Modifying
    @Query("update PokemonProduct set purchased = true where productId = ?1")
    Integer setPurchasedFor(Integer productId);

    List<PokemonProduct> findByPurchased(Boolean purchased);

}
