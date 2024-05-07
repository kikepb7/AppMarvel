package com.enriquepalmadev.appmarvel.domain.feature.character.useCase

import com.enriquepalmadev.appmarvel.data.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.data.feature.character.utils.CharacterError
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Either
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.domain.feature.character.utils.extensions.filterEmptyImageAndDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCharacterUseCase @Inject constructor(
    private val characterListRepository : CharacterRepositoryImpl
){

    suspend fun getCharacterList(): Flow<Either<CharacterError, List<CharacterModel>?>> {
        //Filter to empty description and image
        return flow {
            when (val response = characterListRepository.getCharacterList()) {
                is Either.Success -> {
                    val filteredList = response.data?.filterEmptyImageAndDescription()
                    emit(Either.Success(filteredList))
                }

                is Either.Error -> {
                    emit(Either.Error(response.error))
                }
            }
        }
    }
}

