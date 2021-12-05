package com.example.pokemonapp.model

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.*

interface PokemonAPI {
    @GET
    fun getPokemons(@Url url: String?): Call<JsonResponse>

    @GET("api/v2/pokemon/{id}")
    fun getPokemonDetail(@Path("id") id: String?): Call<PokemonDetail>

}