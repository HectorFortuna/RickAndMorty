package com.hectorfortuna.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hectorfortuna.rickandmorty.databinding.CharacterItemBinding
import com.hectorfortuna.rickandmorty.data.model.Results

class CharacterAdapter(
    private val results: List<Results>
): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CharacterViewHolder(binding)

    }

    override fun getItemCount() = results.count()

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindView(results[position])
    }

    class CharacterViewHolder(
        private val binding: CharacterItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bindView(character: Results){
            binding.run{
                txtNameCharacterItem.text = character.name

                Glide.with(itemView)
                    .load(character.image)
                    .centerCrop()
                    .into(imgItem)
            }
        }
    }



}