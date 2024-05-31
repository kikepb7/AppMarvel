package com.enriquepalmadev.domain_layer.feature.character.repository

import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel

interface CharacterLocalRepository {
    suspend fun  getAllCharacters():List<CharacterModel>?
    suspend fun getCharactersFilterlist(filt: String): List<CharacterModel>?
    suspend fun getCharactersOrderbyNameAZ(): List<CharacterModel>?
    suspend fun getCharactersOrderbyNameZA(): List<CharacterModel>?
    suspend fun getCharactersOrderByFavourites(): List<CharacterModel>?
    suspend fun getCharacterDetail(id: Int): CharacterModel?
    suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean)
    suspend fun deleteAllCharactersFromLocal()

}