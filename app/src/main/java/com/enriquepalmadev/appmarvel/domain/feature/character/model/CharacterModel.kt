package com.enriquepalmadev.appmarvel.domain.feature.character.model


data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailDTO: String
)//TODO [11:59] Borja Hernando Manrique mismo model para la lista y detalle? [12:00] Borja Hernando Manrique si devuelve algun parametro mas el detalle haria uno nuevo, si no está bien asi

