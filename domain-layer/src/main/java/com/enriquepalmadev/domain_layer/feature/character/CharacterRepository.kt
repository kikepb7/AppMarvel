package com.enriquepalmadev.domain_layer.feature.character

import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either

interface CharacterRepository {
    suspend fun getCharacterList(): Either<CharacterErrorDomain, List<CharacterModel>?>
    suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorDomain, CharacterModel?>
}