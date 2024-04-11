package com.enriquepalmadev.appmarvel.data.featureComics.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enriquepalmadev.appmarvel.data.featureComics.database.dao.ComicDao
import com.enriquepalmadev.appmarvel.data.featureComics.database.entities.ComicEntity

@Database(entities = [ComicEntity::class], version = 1)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun getComic() : ComicDao
}

private lateinit var INSTANCE: ComicDatabase

fun getDatabase(context: Context): ComicDatabase {
    synchronized(ComicDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ComicDatabase::class.java,
                "comicDatabase").build()
        }
    }
    return INSTANCE
}