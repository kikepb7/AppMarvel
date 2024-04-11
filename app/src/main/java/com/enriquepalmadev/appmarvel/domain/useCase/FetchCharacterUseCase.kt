package com.enriquepalmadev.appmarvel.domain.useCase

import com.enriquepalmadev.appmarvel.data.repository.CharacterRepositoryImpl
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel


class FetchCharacterUseCase{

    private val characterListRepository= CharacterRepositoryImpl()

    suspend fun fetchCharacterList(): List<CharacterModel>?{
        //Filter to empty description and image
        return characterListRepository.fetchCharacterList()?.filter {item->
            item.description != "" && !item.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
        }
    }

    suspend fun fetchCharacterFilterList(filt: String): List<CharacterModel>?{
        //Filter to empty description and image
        return characterListRepository.fetchCharacterList()?.filter {item->
            item.description != "" && !item.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg") && item.name.startsWith(filt)

        }
    }

    suspend fun fetchCharacterListOrderName(): List<CharacterModel>?{
        //Filter to empty description and image
        return characterListRepository.fetchCharacterList()?.sortedBy { item->
            item.name
        }?.filter {item->
            item.description != "" && !item.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")

        }
    }

    suspend fun fetchCharacterListOrderFavourites(): List<CharacterModel>?{
        //Filter to empty description and image
        return characterListRepository.fetchCharacterList()?.sortedBy { item->
            item.name
        }?.filter {item->
            item.description != "" && !item.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")

        }
    }
}

