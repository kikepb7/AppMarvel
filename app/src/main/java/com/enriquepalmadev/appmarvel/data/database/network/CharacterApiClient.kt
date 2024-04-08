package com.enriquepalmadev.appmarvel.data.database.network

import com.enriquepalmadev.appmarvel.data.database.model.CharacterModel
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiClient {
    @GET("/characters")
    suspend fun getAllCharacters(
        @Query("apiKey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int?= null
    ): List<CharacterModel>

    @GET("/characters{characterId}")
    suspend fun getCharacterById(
        @Query("apiKey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("characterId") characterId: Long?= null
    ): CharacterModel
}