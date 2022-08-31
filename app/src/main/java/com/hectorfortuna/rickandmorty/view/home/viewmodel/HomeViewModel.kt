package com.hectorfortuna.rickandmorty.view.home.viewmodel

import androidx.lifecycle.*
import com.hectorfortuna.rickandmorty.core.State
import com.hectorfortuna.rickandmorty.data.model.CharactersResponse
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepository
import com.hectorfortuna.rickandmorty.di.coroutines.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _response = MutableLiveData<State<CharactersResponse>>()
    val response: LiveData<State<CharactersResponse>> = _response

    fun getCharacters(page:Int){
        viewModelScope.launch {
            try {
                _response.value = State.loading(true)
                val response = withContext(ioDispatcher){
                    repository.getCharacters(page)
                }
                _response.value = State.loading(false)
                _response.value = State.success(response)
            } catch(throwable: Throwable){
                _response.value = State.loading(false)
                _response.value = State.error(throwable)
            }
        }
    }

//    class HomeViewModelProviderFactory(
//        private val repository: CharacterRepository,
//        private val ioDispatcher: CoroutineDispatcher,
//    ) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//                return HomeViewModel(repository, ioDispatcher) as T
//            }
//            throw IllegalArgumentException("Unknown viewModel Class")
//        }
//    }
}