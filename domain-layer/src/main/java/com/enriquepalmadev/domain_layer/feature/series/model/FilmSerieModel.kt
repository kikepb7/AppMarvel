package com.enriquepalmadev.domain_layer.feature.series.model

data class FilmSerieModel(
    val id : Int,
    val title : String,
    val description : String?,
    val thumbnailPath : String,
    val thumbnailExt : String,
    val startYear : Int
)
