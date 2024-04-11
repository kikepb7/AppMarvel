package com.enriquepalmadev.appmarvel.data.utilsData

import com.enriquepalmadev.appmarvel.data.model.ResultDTO
import com.enriquepalmadev.appmarvel.domain.model.CharacterModel

fun List<ResultDTO>.dtoToCharacterListModel(): List<CharacterModel>{
    return this.map {
        it.dtoToCharacterModel()
    }
}

fun ResultDTO.dtoToCharacterModel(): CharacterModel{
    return CharacterModel(
        id = id.toInt(),
        name = name,
        description = description,
        thumbnailDTO = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}