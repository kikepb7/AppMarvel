package com.enriquepalmadev.appmarvel.domain.model

import com.enriquepalmadev.appmarvel.data.database.entities.CharacterEntity
import com.enriquepalmadev.appmarvel.domain.model.Character
import java.io.Serializable

data class Character(
    val id: Long,
    val nombre: String,
    val image: String,
    val descripcion: String
) : Serializable

//fun QuoteModel.toDomain() = Character(id, nombre, image, descripcion)
//fun QuoteEntity.toDomain() = Character(id, nombre, image, descripcion)

