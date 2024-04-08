package com.enriquepalmadev.appmarvel.data.api.dtos

data class MarvelEventsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)