package com.example.pokeapi;

import com.example.pokeapi.Models.Pokemon;

import java.util.List;

public class PokemonList {
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}