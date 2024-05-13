package com.enriquepalmadev.domain_layer.feature.character.repository

import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.domain_layer.feature.commons.Either

interface CharacterRepository {
    suspend fun getCharacterList(): Either<CharacterErrorModel, List<CharacterModel>?>
    suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorModel, CharacterModel?>
}