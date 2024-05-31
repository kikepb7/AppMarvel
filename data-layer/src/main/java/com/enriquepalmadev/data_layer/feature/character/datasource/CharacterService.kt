package com.enriquepalmadev.data_layer.feature.character.datasource

import com.enriquepalmadev.data_layer.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {
    @GET("characters")
    suspend fun getAllCharacters(
    ): Response<CharacterResponseDTO<ResultDTO>>

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
    ): Response<CharacterResponseDTO<ResultDTO>>
}
