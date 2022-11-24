package com.hectorfortuna.rickandmorty.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hectorfortuna.rickandmorty.data.model.Results
import com.hectorfortuna.rickandmorty.databinding.CharacterItemBinding

class CharacterAdapter(
    private val results: List<Results>,
    private val itemClick: ((item: Results) -> Unit)
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding,itemClick)

    }

    override fun getItemCount() = results.count()

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindView(results[position])
    }

    class CharacterViewHolder(
        private val binding: CharacterItemBinding,
        private val itemClick: ((item: Results) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(character: Results) {
            binding.run {
                //nome
                txtNameCharacterItem.text = character.name

                //status
                txtCharacterStatus.text = character.status
                //especies
                txtCharacterSpecies.text = character.species

                Glide.with(itemView)
                    .load(character.image)
                    .centerCrop()
                    .into(imgItem)

                itemView.setOnClickListener{
                    itemClick.invoke(character)

                }
            }
        }
    }


}