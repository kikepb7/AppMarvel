package com.enriquepalmadev.data_layer.feature.character.repository

import com.enriquepalmadev.data_layer.feature.character.database.dao.CharacterDAO
import com.enriquepalmadev.data_layer.feature.character.database.entity.CharacterEntity
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.toCharacterModel
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterLocalRepository
import javax.inject.Inject

class CharacterLocalRepositoryImpl @Inject constructor(
    private val characterDAO: CharacterDAO
): CharacterLocalRepository {

    override suspend fun getAllCharacters(): List<CharacterModel>? {
        val response = characterDAO.getAllCharacters()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersFilterlist(filt: String): List<CharacterModel>? {
        val response = characterDAO.getCharactersFilterlist(filt)
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderbyNameAZ(): List<CharacterModel>? {
        val response = characterDAO.getCharactersOrderbyNameAZ()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderbyNameZA(): List<CharacterModel>? {
        val response = characterDAO.getCharactersOrderbyNameZA()
        return response?.map{
            it.toCharacterModel()
        }
    }

    override suspend fun getCharactersOrderByFavourites(): List<CharacterModel>? {
        val response = characterDAO.getCharactersOrderByFavourites()
        return response?.map {
            it.toCharacterModel()
        }
    }

    override suspend fun getCharacterDetail(id: Int): CharacterModel? {
        val response = characterDAO.getCharacterDetail(id)
        return response?.toCharacterModel()
    }

    override suspend fun modifierFavouriteCharacter(characterId: Int, isFavourite: Boolean) {
        // Obtener el personaje de la base de datos local
        val character = characterDAO.getCharacterDetail(characterId)
        character?.let {
            // Actualizar el personaje en la base de datos local
            characterDAO.updateFavouriteCharacter(it.copy(favourite = isFavourite))
        }
    }

    suspend fun insertAll(charactersList: List<CharacterEntity>) {
        characterDAO.insertAll(charactersList)
    }


    override suspend fun deleteAllCharactersFromLocal() {
        characterDAO.deleteAllCharactersFromLocal()

    }
}