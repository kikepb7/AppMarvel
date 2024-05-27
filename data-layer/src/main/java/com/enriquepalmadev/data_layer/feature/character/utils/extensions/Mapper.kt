package com.enriquepalmadev.data_layer.feature.character.utils.extensions

import com.enriquepalmadev.data_layer.feature.character.database.entity.CharacterEntity
import com.enriquepalmadev.data_layer.feature.character.dto.ResultDTO
import com.enriquepalmadev.data_layer.feature.character.utils.CharacterError
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.domain_layer.feature.character.utils.CharacterErrorModel

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
        thumbnailDTO = "${thumbnail?.path}.${thumbnail?.extension}",
        favourite = false
    )
}

fun CharacterError.toCharacterErrorDomain(): CharacterErrorModel {
     return when (this) {
         is CharacterError.ApiError -> CharacterErrorModel.ApiError(code, message)
         CharacterError.Unauthorized -> CharacterErrorModel.Unauthorized
         CharacterError.UnknownHostError -> CharacterErrorModel.UnknownHostError
     }
}
fun CharacterModel.toCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    description = description,
    thumbnail = thumbnailDTO,
    favourite = false
)
//Character Mapper Database(Entity to Model)
fun CharacterEntity.toCharacterModel(): CharacterModel{

    return CharacterModel(
        id = id.toInt(),
        name = name,
        description = description,
        thumbnailDTO = thumbnail.toString(),
        favourite = favourite
    )
}