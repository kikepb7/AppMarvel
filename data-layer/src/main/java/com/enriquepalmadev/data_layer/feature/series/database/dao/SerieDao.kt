package com.enriquepalmadev.data_layer.feature.series.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity


@Dao
interface SerieDao {
    @Query("UPDATE series SET isFav = :isFav WHERE idSerie = :id")
    suspend fun updateFavSerie(id: Int, isFav: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSeries(series: List<SerieEntity>)

    @Query("SELECT * FROM series")
    suspend fun getAllSeries() : List<SerieEntity>

}