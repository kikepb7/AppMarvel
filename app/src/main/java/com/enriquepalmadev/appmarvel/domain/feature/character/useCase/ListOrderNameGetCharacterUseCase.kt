package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListOrderNameGetCharacterUseCase {

    private val characterListRepositoryImpl= CharacterRepositoryImpl

    suspend fun getCharacterListOrderByName(): Flow<Either<String, List<CharacterModel>?>> {

        return flow{
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error -> flow{
                    emit(Either.Error(response.error))
                } //response.error
                is Either.Success-> flow{
                    val filteredList = response.data?.sortedBy {
                        it.name
                    }?.filter {
                        it.description.isNotEmpty() && !it.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")//Crear extension

                    }
                    emit(Either.Success(filteredList))
                }
            }
        }
    }
}