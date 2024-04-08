package com.enriquepalmadev.appmarvel.data.api.dtos

data class MarvelStoriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemXXDto>,
    val returned: Int
)