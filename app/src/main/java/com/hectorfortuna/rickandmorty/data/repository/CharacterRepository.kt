package com.hectorfortuna.rickandmorty.data.repository

import com.hectorfortuna.rickandmorty.data.model.CharactersResponse

interface CharacterRepository {
    suspend fun getCharacters(page: Int): CharactersResponse
}