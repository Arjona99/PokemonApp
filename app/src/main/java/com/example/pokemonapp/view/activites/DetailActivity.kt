package com.example.pokemonapp.view.activites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.PokemonDetailBinding
import com.example.pokemonapp.model.PokemonAPI
import com.example.pokemonapp.model.PokemonDetail
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: PokemonDetailBinding
    private val BASE_URL = "https://pokeapi.co/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature((Window.FEATURE_NO_TITLE))
        getSupportActionBar()?.hide()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding = PokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val pokemonId = bundle?.getString("pokemon_id")

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val pokemonApi: PokemonAPI = retrofit.create(PokemonAPI::class.java)
        val call: Call<PokemonDetail> = pokemonApi.getPokemonDetail(pokemonId)

        call.enqueue(object: Callback<PokemonDetail> {
            override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
                val details = response.body()
                with(binding) {
                    tvNameDetail.text = details!!.name!!.capitalize()
                    tvHeight.text = "${getString(R.string.height)}: ${details!!.height}${getString(R.string.heightUnits)}"
                    tvWeight.text = "${getString(R.string.weight)}: ${details!!.weight} ${getString(R.string.weightUnits)}"
                    Picasso.get().load(details!!.sprites!!.other!!.officialArtwork!!.frontDefault).into(ivPokemonDetail)
                    ivType1.setImageResource(getTypeImage(details!!.types!![0].typeInfo!!.name!!))
                    if(details!!.types!!.size > 1) {
                        ivType2.setImageResource(getTypeImage(details!!.types!![1].typeInfo!!.name!!))
                    }

                    for ( stat in details.stats!!.subList(0,3)) {
                        addStatLayout(stat.stat!!.statName!!, stat.base_stat!!, true )
                    }
                    for ( stat in details.stats!!.subList(3,6)) {
                        addStatLayout(stat.stat!!.statName!!, stat.base_stat!!, false )
                    }

                    pbDetails.visibility = View.INVISIBLE
                    cvDetail.visibility = View.VISIBLE
                }

            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                Log.d("LOGS", "Respuesta del servidor: ${t.message}")
                //Toast.makeText(this@DetailActivity, getString(R.string.fetchError) , Toast.LENGTH_SHORT).show()
                Toast.makeText(this@DetailActivity, t.message , Toast.LENGTH_SHORT).show()
                binding.pbDetails.visibility = View.INVISIBLE
            }
        })

    }

    private fun addStatLayout(name: String, progress: Int, isLayoutLeft: Boolean) {
        var statLayout: View? = null
        if (isLayoutLeft) {
            statLayout = LayoutInflater.from(this).inflate(R.layout.stat_bar, binding.layoutStats1, false)
        } else {
            statLayout = LayoutInflater.from(this).inflate(R.layout.stat_bar, binding.layoutStats2, false)
        }
        var tvStatName: TextView? = statLayout?.findViewById(R.id.tvStatName)
        var progress_bar: ProgressBar? = statLayout?.findViewById(R.id.progressBar)

        tvStatName!!.text =getString(
            resources.getIdentifier(name.replace("-", "_"), "string", "com.example.pokemonapp")
        )
        progress_bar!!.progress = progress

        if (isLayoutLeft) {
            binding.layoutStats1.addView(statLayout)
        } else {
            binding.layoutStats2.addView(statLayout)
        }
    }

    private fun getTypeImage(type: String): Int {
        return resources.getIdentifier(type, "drawable", "com.example.pokemonapp")
    }
}