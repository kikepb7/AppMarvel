package com.enriquepalmadev.data_layer.feature.series.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enriquepalmadev.data_layer.feature.series.database.dao.SerieDao
import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity

@Database(entities = [SerieEntity::class], version = 1, exportSchema = false)
abstract class SerieDatabase: RoomDatabase() {
    abstract fun serieDao(): SerieDao
}