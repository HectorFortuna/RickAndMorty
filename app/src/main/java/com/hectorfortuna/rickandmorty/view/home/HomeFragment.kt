package com.hectorfortuna.rickandmorty.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.hectorfortuna.rickandmorty.view.home.viewmodel.HomeViewModel
import com.hectorfortuna.rickandmorty.core.Status
import com.hectorfortuna.rickandmorty.databinding.FragmentHomeBinding
import com.hectorfortuna.rickandmorty.data.model.Results
import com.hectorfortuna.rickandmorty.di.ApiServiceModule
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepository
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepositoryImpl
import com.hectorfortuna.rickandmorty.view.home.adapter.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

//    private lateinit var repository: CharacterRepository

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

//        repository = CharacterRepositoryImpl(ApiServiceModule.service)

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

        }
    }
}