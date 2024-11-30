package com.example.pokeapi;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokeapi.Models.Pokemon;
import com.example.pokeapi.Poqueapi.PokeApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class PokemonViewModel extends ViewModel {

    private Retrofit retrofit;
    private Poqueapi.PokeApiService pokeApiService;
    private MutableLiveData<Pokemon> pokemonLiveData;
    private MutableLiveData<List<Pokemon>> pokemonListLiveData; // Nuevo LiveData para la lista de Pokémon

    public PokemonViewModel() {
        // Inicializar MutableLiveData para el Pokémon y la lista de Pokémon
        pokemonLiveData = new MutableLiveData<>();
        pokemonListLiveData = new MutableLiveData<>();

        // Configurar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Poqueapi.PokeApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear la instancia de la API
        pokeApiService = retrofit.create(Poqueapi.PokeApiService.class);
    }

    public LiveData<Pokemon> getPokemonLiveData() {
        return pokemonLiveData;
    }

    public LiveData<List<Pokemon>> getPokemonListLiveData() {
        return pokemonListLiveData; // Obtener la lista de Pokémon
    }

    // Método para obtener un Pokémon por ID
    public void getPokemonById(PokeApiService pokeService) {
        Call<Pokemon> pokeCall = pokeService.getPokemonById(Integer.toString((int) (Math.random() * 807 + 1)));
        pokeCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                // Verificar que la respuesta no sea nula
                Pokemon foundPoke = response.body();
                if (foundPoke != null) {
                    Log.d("POKEMON NAME", foundPoke.getName());
                    Log.d("POKEMON HEIGHT", foundPoke.getHeight());
                    Log.d("POKEMON WEIGHT", foundPoke.getWeight());
                    pokemonLiveData.setValue(foundPoke); // Actualizar el LiveData con el Pokémon encontrado
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                // Manejar el error si la llamada falla
                Log.e("API ERROR", t.getMessage());
            }
        });
    }

    // Método para obtener una lista de Pokémon
    public void getPokemonList(int limit, int offset) {
        Call<PokemonList> call = pokeApiService.getPokemonList(limit, offset);
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Actualizar el LiveData con la lista de Pokémon
                    pokemonListLiveData.setValue(response.body().getResults());
                    // Imprimir los nombres de los Pokémon obtenidos por consola
                    for (Pokemon pokemon : response.body().getResults()) {
                        Log.d("POKEMON NAME", pokemon.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                Log.e("API ERROR", t.getMessage());
            }
        });
    }
}
