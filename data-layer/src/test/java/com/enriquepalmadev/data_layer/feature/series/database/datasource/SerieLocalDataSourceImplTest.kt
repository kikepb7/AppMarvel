package com.enriquepalmadev.data_layer.feature.series.database.datasource

import com.enriquepalmadev.data_layer.feature.series.database.dao.SerieDao
import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SerieLocalDataSourceImplTest {

    private lateinit var serieDao : SerieDao
    private lateinit var dataSource : SerieLocalDataSourceImpl

    // This function is going to be executed before every @Test
    @Before
    fun onBefore(){
        serieDao = mockk()
        dataSource = SerieLocalDataSourceImpl(serieDao)
    }

    @Test
    fun `updateFavSerie updates favorite status in local database`() { runBlocking {
            // Given -> Preparing the initial state of the test
            val idSerie = 1
            val isFavorite = true

            coEvery { serieDao.updateFavSerie(idSerie, isFavorite) } returns Unit

            // When -> Calling to the method that is going to be tested
            dataSource.updateFavSerie(idSerie, isFavorite)

            // Then
            // Verify that updateFavSerie was called with the correct parameters
            coEvery { serieDao.updateFavSerie(idSerie, isFavorite) }
        }
    }

    @Test
    fun `insertAllSeries inserts series into local database`() { runBlocking {
        // Given -> Preparing the initial state of the test
        val series = listOf(
            SerieEntity(1, "Series One", "", "", "", 2020, false),
            SerieEntity(2, "Series Two", "", "", "", 2021, false),
            SerieEntity(3, "Series Three", "", "", "", 2022, false)
        )

        // Configuring the mock to return void
        coEvery { serieDao.insertAllSeries(series) } returns Unit

        // When -> Calling to the method that is going to be tested
        dataSource.insertAllSeries(series)

        // Then
        // Verify that insertAllSeries was called with the correct parameter
        coEvery { serieDao.insertAllSeries(series) }
        }
    }

    @Test
    fun `getAllSeries retrieves all series from local database`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val expectedSeries = listOf(
            SerieEntity(1, "Series One", "", "", "", 2020, false),
            SerieEntity(2, "Series Two", "", "", "", 2021, false),
            SerieEntity(3, "Series Three", "", "", "", 2022, false)
        )

        coEvery { serieDao.getAllSeries() } returns expectedSeries

        // When -> Calling to the method that is going to be tested
        val result = dataSource.getAllSeries()

        // Then
        // Verify that actual response and expected response are equals
        assertEquals(expectedSeries, result)
    }
}