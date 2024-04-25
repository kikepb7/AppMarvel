package com.enriquepalmadev.appmarvel.domain.feature.character

import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacterList(): Either<String, List<CharacterModel>?>
    suspend fun getCharacterDetail(characterId: Int): Either<String, CharacterModel?>
}