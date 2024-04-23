package com.enriquepalmadev.appmarvel.data.feature.character

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import retrofit2.Response

interface CharacterDataSource {
    suspend fun getCharactersFromApi(): Response<CharacterResponseDTO<ResultDTO>>
    suspend fun getCharacterDetailFromApi(characterId: Int): Response<CharacterResponseDTO<ResultDTO>>
}