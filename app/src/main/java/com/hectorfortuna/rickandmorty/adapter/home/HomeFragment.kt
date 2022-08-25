package com.hectorfortuna.rickandmorty.adapter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.hectorfortuna.rickandmorty.adapter.CharacterAdapter
import com.hectorfortuna.rickandmorty.adapter.home.viewmodel.HomeViewModel
import com.hectorfortuna.rickandmorty.core.Status
import com.hectorfortuna.rickandmorty.databinding.FragmentHomeBinding
import com.hectorfortuna.rickandmorty.data.model.Results
import com.hectorfortuna.rickandmorty.data.network.ApiService
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepository
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepositoryImpl
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: CharacterRepository
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

        repository = CharacterRepositoryImpl(ApiService.service)
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO)
            .create(HomeViewModel::class.java)

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
                    it.data?.let {
                        Timber.tag("Sucesso").i(it.toString())
                        setRecyclerView(it.results)
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
        characterAdapter = CharacterAdapter(characterList)
    }
}