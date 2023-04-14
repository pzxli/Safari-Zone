package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.PokemonProduct;
import com.Revature.SafariZoneBackEnd.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private ProductRepo productRepo;


    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Grabs the Pokemon's id number from PokeAPI
     * @param id
     * @param shiny
     * @return a list of pokemon based on the passed id and if they are shiny or not
     */
    public List<PokemonProduct> getByPokemonId(Integer id, Optional<Boolean> shiny)
    {
        List<PokemonProduct> getList = this.productRepo.findByPokemonIdAndPurchasedFalse(id);
        List<PokemonProduct> filteredList = new ArrayList<>();
        if (shiny.isPresent())
        {
            for (PokemonProduct pokemon : getList)
            {
                if (pokemon.getShiny().equals(shiny.get()))
                {
                    filteredList.add(pokemon);
                }
            }
            return filteredList;
        }
        return getList;
    }

    /**
     * Grabs the Pokemon's name from the PokeAPI
     * @param name
     * @param shiny
     * @return a list of pokemon based on the pokemon's name and if they are shiny or not
     */
    public List<PokemonProduct> getByName(String name, Optional<Boolean> shiny) {
        List<PokemonProduct> getList = this.productRepo.findByPokemonNameAndPurchasedFalse(name);
        List<PokemonProduct> filteredList = new ArrayList<>();
        if (shiny.isPresent())
        {
            for (PokemonProduct pokemon : getList)
            {
                if (pokemon.getShiny().equals(shiny.get()))
                {
                    filteredList.add(pokemon);
                }
            }
            return filteredList;
        }
        return getList;
    }

    /**
     * Find Pokemon that are shiny
     * @param shiny
     * @return a list of pokemon that fall into type shiny pokemon.
     */
    public List<PokemonProduct> getByShiny(Boolean shiny) {
        return this.productRepo.findByShinyAndPurchasedFalse(shiny);
    }

    /**
     * Find by pokemon nickname
     * @param nickname
     * @param shiny
     * @return a pokemon based on the nickname passed and whether it is shiny or not
     */
    public List<PokemonProduct> getByNickName(String nickname, Optional<Boolean> shiny) {
        List<PokemonProduct> getList = this.productRepo.findByNicknameNative(nickname);
        List<PokemonProduct> filteredList = new ArrayList<>();
        if (shiny.isPresent())
        {
            for (PokemonProduct pokemon : getList)
            {
                if (pokemon.getShiny().equals(shiny.get()))
                {
                    filteredList.add(pokemon);
                }
            }
            return filteredList;
        }
        return getList;
    }

    /**
     * Grabs the Product id once created
     * @param id
     * @return a single pokemon based on the product id
     */
    public PokemonProduct getByProductId(Integer id) {
        return this.productRepo.findByProductIdAndPurchasedFalse(id);
    }

    /**
     * Creating a pokemon product
     * @param pokemonProduct
     * @return the created pokemon product
     */
    public PokemonProduct savePokemonProduct(PokemonProduct pokemonProduct) {
        return this.productRepo.save(pokemonProduct);
    }

    /**
     * Deleting pokemon product
     * @param productId
     * @return nothing
     */
    public void deletePokemonProduct(Integer productId) {
        this.productRepo.deleteById(productId);
    }

    /**
     * Find all pokemon
     * @return a list of all Pokemon products
     */
    public List<PokemonProduct> getAllProducts() {
        return this.productRepo.findAll();
    }

    /**
     * update product to purchased
     * @return productId for product purchased
     */
    public Integer purchaseProduct(Integer productId) {
        return this.productRepo.setPurchasedFor(productId);
    }

    /**
     * Find list of purchased products
     * @return a list of Pokemon purchased
     */
    public List<PokemonProduct> getByPurchased(Boolean purchased) { return this.productRepo.findByPurchased(purchased); }
}
