package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    suspend fun getAllCharacters(
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int?= null
    ): Response<CharacterResponseDTO<ResultDTO>>

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int? = null,
        @Query("hash") hash: String,
        @Query("ts") ts: String
    ): Response<CharacterResponseDTO<ResultDTO>>
}
