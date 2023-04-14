package com.Revature.SafariZoneBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pokemon_product")
public class PokemonProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private Integer pokemonId;

    @Column(nullable = false)
    private String pokemonName;

    @Column(nullable = false)
    private Integer prodHeight;

    @Column(nullable = false)
    private Integer prodWeight;

    @Column(nullable = false)
    private Double prodPrice;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String description;

    @Column(columnDefinition = "boolean default false")
    private Boolean shiny;

    @Column(columnDefinition = "boolean default false")
    private Boolean purchased;
}
