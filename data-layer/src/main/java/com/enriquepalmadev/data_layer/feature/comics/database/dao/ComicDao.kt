package com.enriquepalmadev.data_layer.feature.comics.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriquepalmadev.data_layer.feature.comics.database.entities.ComicEntity
import com.enriquepalmadev.data_layer.feature.comics.database.entities.FavoriteComicEntity

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic_table ORDER BY title ASC")
    suspend fun findAllComics(): List<ComicEntity>

    @Query("SELECT * FROM comic_table WHERE id = :comicId")
    suspend fun findComicById(comicId: Int): ComicEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComics(comicList: List<ComicEntity>)

    @Query("DELETE FROM comic_table")
    suspend fun deleteAllComics()

    @Query("DELETE FROM comic_table WHERE id = :comicId")
    suspend fun deleteComicById(comicId: Int)

    @Query("SELECT * FROM favorite_comics WHERE isFavorite = 1")
    suspend fun getFavoriteComicList(): List<FavoriteComicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(comic: FavoriteComicEntity)
    @Query("DELETE FROM favorite_comics WHERE id = :comicId")
    suspend fun removeComic(comicId: Int)
}