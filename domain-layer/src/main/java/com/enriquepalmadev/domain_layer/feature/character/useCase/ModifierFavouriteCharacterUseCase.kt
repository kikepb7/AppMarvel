package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import javax.inject.Inject

class ModifierFavouriteCharacterUseCase @Inject constructor(
    private val characterRepositoryImpl: CharacterRepository
) {
    suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean){
        characterRepositoryImpl.modifierFavouriteCharacter(characterId, isFavourite)
    }
}