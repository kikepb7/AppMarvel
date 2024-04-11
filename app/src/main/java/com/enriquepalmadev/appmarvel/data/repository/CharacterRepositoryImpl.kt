package com.enriquepalmadev.appmarvel.data.repository

import com.enriquepalmadev.appmarvel.data.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.appmarvel.data.utilsData.dtoToCharacterListModel
import com.enriquepalmadev.appmarvel.data.utilsData.dtoToCharacterModel
import com.enriquepalmadev.appmarvel.domain.CharacterRepository
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel


class CharacterRepositoryImpl: CharacterRepository {

    private val remoteDataSource = CharacterRemoteDataSource()

    //Mapeando la clase CharacterDTO a CharacterModel

    override suspend fun fetchCharacterList(): List<CharacterModel>? {
        return remoteDataSource.fetchCharactersFromApi().data?.results?.dtoToCharacterListModel()//Dto to Model
    }

    override suspend fun fetchCharacterDetail(characterId: Int): CharacterModel? {
        return remoteDataSource.fetchCharacterDetailFromApi(characterId).data?.results?.get(0)?.dtoToCharacterModel()//Dto to Model
    }


    /*
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
    }*/

}