package com.enriquepalmadev.appmarvel.data

import java.io.Serializable

data class FilmsSeriesDataclass (
    val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val cover: String
) : Serializable