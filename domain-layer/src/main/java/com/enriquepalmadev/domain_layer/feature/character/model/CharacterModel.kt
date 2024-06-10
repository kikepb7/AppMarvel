package com.enriquepalmadev.domain_layer.feature.character.model


data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailDTO: String,
    val favourite: Boolean
)

