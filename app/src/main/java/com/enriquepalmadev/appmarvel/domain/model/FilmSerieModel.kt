package com.enriquepalmadev.appmarvel.domain.model

import java.io.Serializable

data class FilmSerieModel (
    val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val cover: String
) : Serializable