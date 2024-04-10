package com.enriquepalmadev.appmarvel.domain.models

import java.io.Serializable

data class FilmSerie(
    val id : Int,
    val title : String,
    val description : String?,
    val thumbnailPath : String,
    val thumbnailExt : String
): Serializable
