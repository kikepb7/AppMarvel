package com.enriquepalmadev.appmarvel.domain.useCase

import com.enriquepalmadev.appmarvel.data.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel

class FetchCharacterDetailUseCase {

    private val characterListRepository= CharacterRepositoryImpl()

    suspend fun fetchCharacterDetail(characterId: Int): CharacterModel?{
        return characterListRepository.fetchCharacterDetail(characterId)
    }
}

