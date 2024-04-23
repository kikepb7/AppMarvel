package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utilsData.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetCharacterUseCase{

    private val characterListRepository= CharacterRepositoryImpl
    /*private val _characterListState = MutableStateFlow<List<CharacterModel>?>(null)
    val characterListState: StateFlow<List<CharacterModel>?> = _characterListState
     */

    suspend fun getCharacterList(): Flow<Either<String, List<CharacterModel>?>> {
        //Filter to empty description and image
        return flow {
            when (val response = characterListRepository.getCharacterList()) {
                is Either.Success -> {
                    val filteredList = response.data?.filter { item ->
                        item.description.isNotEmpty() && !item.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                    }
                    emit(Either.Success(filteredList))
                }

                is Either.Error -> {
                    emit(Either.Error(response.error))
                }//response.error
            }
        }
    }
}

