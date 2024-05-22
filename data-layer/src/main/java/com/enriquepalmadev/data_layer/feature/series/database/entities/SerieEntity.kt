package com.enriquepalmadev.data_layer.feature.series.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SerieEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("idSerie")
    val idSerie : Int,
    @ColumnInfo("titleSerie")
    val titleSerie : String,
    @ColumnInfo("descriptionSerie")
    val descriptionSerie : String?,
    @ColumnInfo("thumbnailPathSerie")
    val thumbnailPathSerie : String,
    @ColumnInfo("thumbnailExtSerie")
    val thumbnailExtSerie : String,
    @ColumnInfo("startYearSerie")
    val startYearSerie : Int,
    @ColumnInfo("favSerie")
    val isFav : Boolean
)
