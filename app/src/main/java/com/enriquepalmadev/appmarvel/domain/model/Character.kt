package com.enriquepalmadev.appmarvel.domain.model

import java.io.Serializable

data class Character(
    val id: Long,
    val nombre: String,
    val image: String,
    val descripcion: String
) : Serializable
