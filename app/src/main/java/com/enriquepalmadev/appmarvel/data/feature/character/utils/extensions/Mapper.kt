package com.enriquepalmadev.appmarvel.data.feature.character.utils.extensions

import com.enriquepalmadev.appmarvel.data.feature.character.dto.ResultDTO
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel

fun List<ResultDTO>.toCharacterListModel(): List<CharacterModel>?{
    return this.map {
        it.toCharacterModel()
    }
}

fun ResultDTO.toCharacterModel(): CharacterModel {
    return CharacterModel(
        id = id.toInt(),
        name = name,
        description = description,
        thumbnailDTO = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}