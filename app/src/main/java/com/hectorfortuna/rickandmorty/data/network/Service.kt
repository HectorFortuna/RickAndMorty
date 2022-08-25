package com.hectorfortuna.rickandmorty.data.network

import com.hectorfortuna.rickandmorty.data.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharactersResponse
}