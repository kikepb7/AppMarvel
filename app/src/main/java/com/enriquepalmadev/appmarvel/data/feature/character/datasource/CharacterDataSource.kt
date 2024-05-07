package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.dto.CharacterResponseDTO
import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either

interface CharacterDataSource {
    suspend fun getCharactersFromApi(): Either<CharacterError, CharacterResponseDTO<ResultDTO>>
    suspend fun getCharacterDetailFromApi(characterId: Int): Either<CharacterError, CharacterResponseDTO<ResultDTO>>
}