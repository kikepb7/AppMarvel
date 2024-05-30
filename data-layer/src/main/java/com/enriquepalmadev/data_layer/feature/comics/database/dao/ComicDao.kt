package com.enriquepalmadev.data_layer.feature.comics.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriquepalmadev.data_layer.feature.comics.database.entities.ComicEntity

@Dao
interface ComicDao {

    @Query("SELECT * FROM comic_table ORDER BY title ASC")
    suspend fun findAllComics(): List<ComicEntity>

    @Query("SELECT * FROM comic_table WHERE id = :comicId")
    suspend fun findComicById(comicId: Int): ComicEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComics(comicList: List<ComicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(comic: ComicEntity)

    @Query("DELETE FROM comic_table")
    suspend fun deleteAllComics()

    @Query("DELETE FROM comic_table WHERE id = :comicId")
    suspend fun deleteComicById(comicId: Int)

    @Query("SELECT * FROM comic_table WHERE isFavorite = 1")
    suspend fun getFavoriteComicList(): List<ComicEntity>
}