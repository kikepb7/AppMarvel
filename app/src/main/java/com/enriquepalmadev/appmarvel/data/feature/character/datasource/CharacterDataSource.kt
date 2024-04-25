package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO

interface CharacterDataSource {
    suspend fun getCharactersFromApi(): CharacterResponseDTO<ResultDTO>
    suspend fun getCharacterDetailFromApi(characterId: Int): CharacterResponseDTO<ResultDTO>
}