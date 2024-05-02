package com.enriquepalmadev.domain_layer.feature.comics.model

data class ComicModel(
    val id: Int,
    val title: String,
    val description: String?,
    val pageCount: Int,
    val thumbnail: String
)