package com.enriquepalmadev.appmarvel.data

import com.enriquepalmadev.appmarvel.data.model.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.model.ResultDTO

interface CharacterDataSource {
    suspend fun fetchCharactersFromApi(): CharacterResponseDTO<ResultDTO>
    suspend fun fetchCharacterDetailFromApi(characterId: Int): CharacterResponseDTO<ResultDTO>
}