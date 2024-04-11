package com.enriquepalmadev.appmarvel.data.featureComics.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriquepalmadev.appmarvel.data.featureComics.database.entities.ComicEntity

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si hay un conflicto lo reemplaza
    suspend fun insertComics(comics : List<ComicEntity>)

    @Query("SELECT * FROM comic_table")
    suspend fun getComics() : List<ComicEntity>

    @Query("SELECT * FROM comic_table WHERE isFavorite = 1")
    suspend fun getFavoriteComics(): List<ComicEntity>

    @Query("DELETE FROM comic_table")
    suspend fun deleteComics()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(comic: ComicEntity)

    @Delete
    suspend fun removeFromFavorites(comicId: Int)

}