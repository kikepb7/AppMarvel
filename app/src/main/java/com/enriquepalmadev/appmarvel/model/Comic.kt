package com.enriquepalmadev.appmarvel.model

import java.io.Serializable

data class Comic(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val image: String
): Serializable
