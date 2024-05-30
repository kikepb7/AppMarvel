package com.enriquepalmadev.data_layer.feature.comics.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enriquepalmadev.data_layer.feature.comics.database.dao.ComicDao
import com.enriquepalmadev.data_layer.feature.comics.database.entities.ComicEntity
import com.enriquepalmadev.data_layer.feature.comics.database.entities.FavoriteComicEntity

@Database(entities = [ComicEntity::class],[FavoriteComicEntity::class], version = 1)
abstract class ComicDatabase: RoomDatabase() {

    abstract fun getComicDao(): ComicDao
}