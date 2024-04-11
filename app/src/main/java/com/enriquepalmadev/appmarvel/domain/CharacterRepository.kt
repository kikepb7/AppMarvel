package com.enriquepalmadev.appmarvel.domain

import com.enriquepalmadev.appmarvel.domain.model.CharacterModel

interface CharacterRepository {
    suspend fun fetchCharacterList(): List<CharacterModel>?
    suspend fun fetchCharacterDetail(characterId: Int): CharacterModel?
}