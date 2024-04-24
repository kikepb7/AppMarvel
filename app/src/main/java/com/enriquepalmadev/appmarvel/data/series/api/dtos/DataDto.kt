package com.enriquepalmadev.appmarvel.data.series.api.dtos

data class DataDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<MarvelFilmSerieItemDto>,
    val total: Int
)