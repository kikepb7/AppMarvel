package com.enriquepalmadev.appmarvel.data.database

/*import com.enriquepalmadev.appmarvel.data.database.dao.CharacterDao
import com.enriquepalmadev.appmarvel.data.database.entities.CharacterEntity
import com.enriquepalmadev.appmarvel.data.database.model.CharacterModel
import com.enriquepalmadev.appmarvel.data.database.network.CharacterService
import com.enriquepalmadev.appmarvel.domain.model.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: CharacterService,
    private val characterDao: CharacterDao
) {


    suspend fun getAllCharactersFromApi(): List<Character>{
        val response = List<CharacterModel> = api.getCharacters()
        return response.map {
            it.toDomain()
        }
    }


    suspend fun getAllCharactersFromDatabase(): List<Character>{
        val response: List<CharacterEntity> = characterDao.getAllCharacters()
        return response.map { it.toDomain() }
    }

    suspend fun insertCharacters(characters: List<CharacterEntity>){
        characterDao.insertAll(characters)
    }

    suspend fun clearCharacters(){
        characterDao.deleteAllCharactersFromLocal()
    }

}*/