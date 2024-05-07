package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.feature.character.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain
import com.enriquepalmadev.domain_layer.feature.character.utils.Either
import com.enriquepalmadev.domain_layer.feature.character.utils.extensions.filterEmptyImageAndDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListOrderFavouritesGetCharacterUseCase @Inject constructor(
    private val characterListRepositoryImpl : CharacterRepository
){

    suspend fun getCharacterListOrderFavourites(): Flow<Either<CharacterErrorDomain, List<CharacterModel>?>> {
        return flow {
            when (val response = characterListRepositoryImpl.getCharacterList()){
                is Either.Error -> {
                    emit(Either.Error(response.error))
                }
                is Either.Success-> {
                    var filteredList = response.data?.sortedBy { item->
                        item.name
                    }?.filterEmptyImageAndDescription()
                    emit(Either.Success(filteredList))
                }
            }
        }
    }
}