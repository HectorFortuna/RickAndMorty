package com.hectorfortuna.rickandmorty.view.detail.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hectorfortuna.rickandmorty.data.model.Results
import com.hectorfortuna.rickandmorty.databinding.FragmentDetailBinding
import com.hectorfortuna.rickandmorty.view.detail.adapter.EpisodeAdapter

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var character: Results

    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = arguments?.getParcelable<Results>("CHARACTER") as Results

        setDetailTexts()
        setImage()
        setRecyclerView(character.episode)

    }

    private fun setDetailTexts() {
        binding.apply {
            txtNameDetail.text = character.name
            txtSpeciesDetail.text = character.species
            originResponse.text = character.origin.name
            locationResponse.text = character.location.name
        }
    }

    private fun setImage() {
        binding.apply {
            Glide.with(this@DetailFragment)
                .load(character.image)
                .centerCrop()
                .into(imageDetail)
        }
    }

    private fun setRecyclerView(episodeList: List<String> = character.episode) {
        setAdapter(episodeList)
        binding.rvEpisodeDetail.apply {
            setHasFixedSize(true)
            adapter = episodeAdapter
        }
    }

    private fun setAdapter(episodeList: List<String> = character.episode) {
        episodeAdapter = EpisodeAdapter(episodeList) {
            openUrl(it)
        }
    }

    private fun openUrl(utl: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(utl)
        startActivity(i)
    }
}