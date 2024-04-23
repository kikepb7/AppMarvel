package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FiltListGetCharacterUseCase
{
    private val characterListRepositoryImpl= CharacterRepositoryImpl

    suspend fun getCharacterFilterList(filt: String): Flow<Either<String, List<CharacterModel>?>> {
        return flow {
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error -> {
                    emit(Either.Error(response.error))
                } //response.error
                is Either.Success-> {
                    val filteredList = response.data?.filter{
                        it.description != "" && !it.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg") && it.name.startsWith(filt)
                    }
                    emit(Either.Success(filteredList))
                }
            }
        }
    }
}