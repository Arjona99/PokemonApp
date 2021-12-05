package com.example.pokemonapp.model

import com.google.gson.annotations.SerializedName

class JsonResponse {
    @SerializedName("results")
    var results: List<Pokemon>? = null
}

class Pokemon {
    @SerializedName("url")
    var url: String? = null

    @SerializedName("name")
    var name: String? = null

    var id: Int? = null
}
