package com.enriquepalmadev.appmarvel.domain.featureComics.model

data class ComicModel(
    val id: Int,
    val title: String,
    val description: String?,
    val pageCount: Int,
    val thumbnail: String
)