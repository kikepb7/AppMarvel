package com.enriquepalmadev.appmarvel.data.series.api.dtos

data class MarvelComicsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemDto>,
    val returned: Int
)