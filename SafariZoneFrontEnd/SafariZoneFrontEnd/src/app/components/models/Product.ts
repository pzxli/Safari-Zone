export interface Product{
    productId: number;
    pokemonId: number;
    pokemonName: string;
    pokeSpriteUrl : string;
    prodHeight: number;
    prodWeight: number;
    prodPrice: number;
    nickname: string;
    description: string;
    shiny: boolean;
    tinySpriteUrl: string;
    purchased: boolean;
    inCart: boolean;
}