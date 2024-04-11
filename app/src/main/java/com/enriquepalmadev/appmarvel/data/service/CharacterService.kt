package com.enriquepalmadev.appmarvel.data.service

import com.enriquepalmadev.appmarvel.data.model.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.model.ResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    suspend fun getAllCharacters(
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int?= null
    ): CharacterResponseDTO<ResultDTO>

    @GET("characters{characterId}")
    suspend fun getCharacterById(
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("characterId") characterId: Int? = null
    ): CharacterResponseDTO<ResultDTO>
}
