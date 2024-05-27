package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import com.enriquepalmadev.domain_layer.feature.character.utils.extensions.filterEmptyImageAndDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListOrderFavouritesGetCharacterUseCase @Inject constructor(
    private val characterListRepositoryImpl : CharacterRepository
){

    suspend fun getCharacterListOrderFavourites(): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {
        return flow {
            val localCharacters = characterListRepositoryImpl.getCharactersOrderByFavourites()
            emit(Either.Success(localCharacters))
        }
    }
}