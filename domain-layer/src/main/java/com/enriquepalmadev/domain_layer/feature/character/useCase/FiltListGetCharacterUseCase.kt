package com.enriquepalmadev.domain_layer.feature.character.useCase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterLocalRepository
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FiltListGetCharacterUseCase @Inject constructor(
    private val characterLocalRepository: CharacterLocalRepository
)
{
    suspend fun getCharacterFilterList(filt: String): Flow<Either<CharacterErrorModel, List<CharacterModel>?>> {
        return flow {
            val localCharacters = characterLocalRepository.getCharactersFilterlist(filt)
            if(localCharacters != null) {
                emit(Either.Success(localCharacters))
            }else{
                emit(Either.Error(CharacterErrorModel.UnknownHostError))
            }
        }
    }
}