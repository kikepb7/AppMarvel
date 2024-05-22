package com.enriquepalmadev.data_layer.feature.series.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity


@Dao
interface SerieDao {
    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<SerieEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE) // Idk if it is right
    suspend fun updateFavSerie(fav : Boolean)

    @Query("SELECT * FROM SERIES WHERE idSerie = idSerie")
    suspend fun getSerieById(idSerie : Int): SerieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSeries(series: List<SerieEntity>)
    @Query("DELETE FROM series")
    suspend fun clearAllSeries()
}