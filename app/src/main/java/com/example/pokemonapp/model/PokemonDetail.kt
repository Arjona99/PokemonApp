package com.example.pokemonapp.model

import com.google.gson.annotations.SerializedName

class PokemonDetail {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("height")
    var height: Int? = null

    @SerializedName("weight")
    var weight: Int? = null

    @SerializedName("stats")
    var stats: List<StatInfo>? = null

    @SerializedName("sprites")
    var sprites: Sprites? = null

    @SerializedName("types")
    var types: List<Type>? = null
}

class StatInfo {
    @SerializedName("base_stat")
    var base_stat: Int? = null

    @SerializedName("stat")
    var stat: Stat? = null
}

class Stat {
    @SerializedName("name")
    var statName: String? = null
}

class Sprites{
    @SerializedName("other")
    var other: Other? = null
}

class Other{
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork? = null
}

class OfficialArtwork{
    @SerializedName("front_default")
    var frontDefault: String? = null
}

class Type{
    @SerializedName("type")
    var typeInfo: TypeInfo? = null
}

class TypeInfo{
    @SerializedName("name")
    var name: String? = null
}