package com.enriquepalmadev.appmarvel.domain.series.models

data class FilmSerieModel(
    val id : Int,
    val title : String,
    val description : String?,
    val thumbnailPath : String,
    val thumbnailExt : String,
    val startYear : Int
)
