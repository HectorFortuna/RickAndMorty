package com.hectorfortuna.rickandmorty.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.hectorfortuna.rickandmorty.R
import com.hectorfortuna.rickandmorty.core.Status
import com.hectorfortuna.rickandmorty.data.model.Results
import com.hectorfortuna.rickandmorty.databinding.FragmentHomeBinding
import com.hectorfortuna.rickandmorty.view.home.adapter.CharacterAdapter
import com.hectorfortuna.rickandmorty.view.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var characterAdapter: CharacterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getCharacters()
        observeVMEvents()
    }

    private fun getCharacters(page: Int = 1) {
        viewModel.getCharacters(page)
    }

    private fun observeVMEvents() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        Timber.tag("Sucesso").i(response.toString())
                        setRecyclerView(response.results)
                    }
                }
                Status.ERROR -> {
                    Timber.tag("Erro").e(it.error)
                }
                Status.LOADING ->{}
            }

        }
    }

    private fun setRecyclerView(characterList: List<Results>) {
        setAdapter(characterList)
        binding.rvHomeFragment.apply {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun setAdapter(characterList: List<Results>) {
        characterAdapter = CharacterAdapter(characterList){
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable("CHARACTER", it)
                }
            )
        }
    }
}