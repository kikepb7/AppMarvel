package com.enriquepalmadev.appmarvel.data.api.dtos

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<MarvelFilmSerieItemDto>,
    val total: Int
)