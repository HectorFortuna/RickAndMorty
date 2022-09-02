package com.hectorfortuna.rickandmorty.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hectorfortuna.rickandmorty.databinding.DetailItemBinding

class EpisodeAdapter(
    private val episodes: List<String>,
    private val itemClick: ((item: String) -> Unit)
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding, itemClick)

    }

    override fun getItemCount() = episodes.count()

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindView(episodes[position])

    }

    class EpisodeViewHolder(
        private val binding: DetailItemBinding,
        private val itemClick: ((item: String) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(episodes: String) {

            binding.run {
                txtEpisodesName.text = episodes
            }

            itemView.setOnClickListener{
                itemClick.invoke(episodes)
            }
        }
    }
}