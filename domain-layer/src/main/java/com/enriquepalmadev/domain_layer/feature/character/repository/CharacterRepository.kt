package com.enriquepalmadev.domain_layer.feature.character.repository

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel

interface CharacterRepository {
    suspend fun getCharacterList(): Either<CharacterErrorModel, List<CharacterModel>?>
    suspend fun getCharacterDetail(characterId: Int): Either<CharacterErrorModel, CharacterModel?>
    suspend fun clearCharacters()
    suspend fun getCharacterListFromDatabase(): List<CharacterModel>?
    suspend fun getCharacterFilterListFromDatabase(filt: String): List<CharacterModel>?
    suspend fun getCharactersOrderByNameAZ(): List<CharacterModel>?
    suspend fun getCharactersOrderByNameZA(): List<CharacterModel>?
    suspend fun getCharacterDetailFromDatabase(characterId: Int): CharacterModel?
    suspend fun getCharactersOrderByFavourites(): List<CharacterModel>?
    suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean)
}