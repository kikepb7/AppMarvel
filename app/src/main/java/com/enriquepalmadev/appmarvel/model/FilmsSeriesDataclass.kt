package com.enriquepalmadev.appmarvel.model

import java.io.Serializable

data class FilmsSeriesDataclass (
    val id: Int,
    val name: String,
    val description: String,
    val cover: String
) : Serializable