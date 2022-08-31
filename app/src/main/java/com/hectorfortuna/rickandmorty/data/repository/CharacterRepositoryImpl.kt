package com.hectorfortuna.rickandmorty.data.repository

import com.hectorfortuna.rickandmorty.data.model.CharactersResponse
import com.hectorfortuna.rickandmorty.data.network.Service
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: Service) : CharacterRepository {
    override suspend fun getCharacters(page: Int): CharactersResponse =
        api.getCharacters(page = 1)
}