package com.enriquepalmadev.appmarvel.domain.useCase

import com.enriquepalmadev.appmarvel.data.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel


class FetchCharacterUseCase{

    private val characterListRepository= CharacterRepositoryImpl()

    suspend fun fetchCharacterList(): List<CharacterModel>?{
        return characterListRepository.fetchCharacterList()
    }
}

