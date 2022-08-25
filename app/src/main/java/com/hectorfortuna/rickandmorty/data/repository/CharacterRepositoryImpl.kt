package com.hectorfortuna.rickandmorty.data.repository

import com.hectorfortuna.rickandmorty.data.model.CharactersResponse
import com.hectorfortuna.rickandmorty.data.network.Service

class CharacterRepositoryImpl(private val api: Service): CharacterRepository {
    override suspend fun getCharacters(page: Int): CharactersResponse =
        api.getCharacters(page = 1)
}