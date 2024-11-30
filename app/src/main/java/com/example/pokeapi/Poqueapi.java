package com.example.pokeapi;

import com.example.pokeapi.Models.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Poqueapi {

    public interface PokeApiService {
        String BASE_URL = "https://pokeapi.co/api/v2/";
        @GET("pokemon/{id}")
        Call<Pokemon> getPokemonById(@Path("id") String id);

        @GET("pokemon")
        Call<PokemonList> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    }
}