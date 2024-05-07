package com.enriquepalmadev.appmarvel.domain.feature.character.utils.extensions

import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel

fun List<CharacterModel>.filterEmptyImageAndDescription(): List<CharacterModel>?{
    return this.filter {character ->
        character.description.isNotEmpty() && !character.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
    }
}

fun List<CharacterModel>.filterEmptyImageAndDescriptionAndName(name: String): List<CharacterModel>?{
    return this.filter {character ->
        character.description.isNotEmpty() && !character.thumbnailDTO.equals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg") && character.name.startsWith(name)
    }
}