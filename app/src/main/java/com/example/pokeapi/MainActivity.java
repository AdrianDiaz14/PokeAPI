package com.example.pokeapi;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokeapi.Models.Pokemon;

public class MainActivity extends AppCompatActivity {
    private PokemonViewModel pokemonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        // Llamar al método para obtener la lista de Pokémon
        pokemonViewModel.getPokemonList(20, 0); // Obtén los primeros 20 Pokémon

        // Observar los cambios en la lista de Pokémon
        pokemonViewModel.getPokemonListLiveData().observe(this, pokemonList -> {
            if (pokemonList != null) {
                for (Pokemon pokemon : pokemonList) {
                    Log.d("POKEMON LIST", pokemon.getName());
                }
            }
        });
    }
}