package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterLocalRepository
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import javax.inject.Inject

class ModifierFavouriteCharacterUseCase @Inject constructor(
    private val characterLocalRepository: CharacterLocalRepository
) {
    suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean){
        characterLocalRepository.modifierFavouriteCharacter(characterId, isFavourite)
    }
}