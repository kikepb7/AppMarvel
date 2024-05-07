package com.enriquepalmadev.data_layer.feature.character.utils.extensions

import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import com.enriquepalmadev.data_layer.feature.character.utils.CharacterError
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorDomain

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

fun CharacterError.toCharacterErrorDomain(): CharacterErrorDomain {
     return when (this) {
         is CharacterError.ApiError -> CharacterErrorDomain.ApiError(code, message)
         CharacterError.Unauthorized -> CharacterErrorDomain.Unauthorized
         CharacterError.UnknownHostError -> CharacterErrorDomain.UnknownHostError
     }
}