package com.enriquepalmadev.appmarvel.data.api.dtos

data class MarvelCharactersDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)