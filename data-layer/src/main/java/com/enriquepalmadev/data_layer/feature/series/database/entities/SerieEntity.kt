package com.enriquepalmadev.data_layer.feature.series.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "SERIES")
data class SerieEntity (
    @ColumnInfo("idSerie")
    val idSerie : Int,
    val titleSerie : String,
    val descriptionSerie : String?,
    val thumbnailPathSerie : String,
    val thumbnailExtSerie : String,
    val startYearSerie : Int,
    val favSerie : Boolean
)
