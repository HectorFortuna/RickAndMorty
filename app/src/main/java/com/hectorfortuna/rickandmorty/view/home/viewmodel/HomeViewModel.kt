package com.hectorfortuna.rickandmorty.view.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hectorfortuna.rickandmorty.core.State
import com.hectorfortuna.rickandmorty.data.model.CharactersResponse
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepository
import com.hectorfortuna.rickandmorty.di.coroutines.qualifiers.Io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository,
    @Io private val ioDispatcher: CoroutineDispatcher
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
                _response.value = State.success(response)
            } catch(throwable: Throwable){
                _response.value = State.loading(false)
                _response.value = State.error(throwable)
            }
        }
    }
}