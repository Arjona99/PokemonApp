package com.example.pokemonapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.databinding.ListItemBinding

class Adapter(context: Context, pokemons: List<Pokemon>, onItemListener: OnItemListener):
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val pokemons = pokemons
    private val mOnItemListener = onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater)

        return ViewHolder(binding, mOnItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(pokemons[position])
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    interface OnItemListener{
        fun onItemClick(game: Pokemon)
    }


    class ViewHolder(binding: ListItemBinding, onItemListener: OnItemListener): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private val tvPokemonName = binding.tvPokemonName
        private val onItemListener = onItemListener
        private lateinit var pokemon: Pokemon

        init{
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(pokemon)
        }

        fun bindData(item: Pokemon){
            tvPokemonName.text = item.name
            pokemon = item
        }

    }
}