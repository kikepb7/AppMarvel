package com.enriquepalmadev.appmarvel.data.series.api.dtos

data class MarvelCreatorsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemXDto>,
    val returned: Int
)