package com.enriquepalmadev.appmarvel.data.featureComics.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.enriquepalmadev.appmarvel.data.featureComics.database.entities.ComicEntity

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic_table")
    suspend fun findAllComics() : List<ComicEntity>
}