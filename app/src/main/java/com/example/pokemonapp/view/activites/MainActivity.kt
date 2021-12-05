package com.example.pokemonapp.view.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokemonapp.model.JsonResponse
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.model.PokemonAPI
import com.example.pokemonapp.model.PokemonDetail
import com.example.pokemonapp.view.adapter.Adapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Adapter.OnItemListener {

    private val BASE_URL = "https://pokeapi.co/"
    private val LOGTAG = "LOGS"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature((Window.FEATURE_NO_TITLE))
        getSupportActionBar()?.hide()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokemonApi: PokemonAPI = retrofit.create(PokemonAPI::class.java)

        val call: Call<JsonResponse> = pokemonApi.getPokemons("api/v2/pokemon?limit=150")

        call.enqueue(object: Callback<JsonResponse>{
            override fun onResponse(call: Call<JsonResponse>, response: Response<JsonResponse>) {
                binding.pbConexion.visibility = View.INVISIBLE

                val adaptador = Adapter(this@MainActivity, response.body()!!.results!!, this@MainActivity)

                with(binding) {
                    rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvPokemon.adapter = adaptador
                }

            }

            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.fetchError) , Toast.LENGTH_SHORT).show()
                binding.pbConexion.visibility = View.INVISIBLE
            }
        })


    }

    override fun onItemClick(pokemon: Pokemon) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        val parametros = Bundle()
        parametros.putString("pokemon_id", pokemon.id.toString())
        intent.putExtras(parametros)
        startActivity(intent)
    }
}