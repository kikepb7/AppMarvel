package com.enriquepalmadev.data_layer.feature.character.datasource

import com.enriquepalmadev.data_layer.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import com.enriquepalmadev.data_layer.feature.character.utils.CharacterError
import com.enriquepalmadev.domain_layer.commons.Either

interface CharacterDataSource {
    suspend fun getCharactersFromApi(): Either<CharacterError, CharacterResponseDTO<ResultDTO>>
    suspend fun getCharacterDetailFromApi(characterId: Int): Either<CharacterError, CharacterResponseDTO<ResultDTO>>
}