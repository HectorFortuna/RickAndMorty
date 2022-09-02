package com.hectorfortuna.rickandmorty.view.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CharacterRepository
): ViewModel() {
}