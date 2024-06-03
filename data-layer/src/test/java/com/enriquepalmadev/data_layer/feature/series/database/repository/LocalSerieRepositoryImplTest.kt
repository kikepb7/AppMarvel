package com.enriquepalmadev.data_layer.feature.series.database.repository

import com.enriquepalmadev.data_layer.feature.series.database.datasource.SerieLocalDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.repository.LocalSerieRepositoryImpl
import com.enriquepalmadev.data_layer.feature.series.utils.modelToSerieListEntity
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalSerieRepositoryImplTest {

    private lateinit var dataSource: SerieLocalDataSourceImpl
    private lateinit var repository : LocalSerieRepositoryImpl

    // This function is going to be executed before every @Test
    @Before
    fun onBefore(){
        dataSource = mockk()
        repository = LocalSerieRepositoryImpl(dataSource)
    }

    @Test
    fun `updateFavSerie updates favorite status in local data source`() {
        runBlocking {
            // Given -> Object mocked
            val id = 1
            val isFavorite = true

            // Configuring the mock to return void
            coEvery { repository.updateFavSerie(id, isFavorite) } returns Unit

            // When -> Calling to the method that is going to be tested
            repository.updateFavSerie(id, isFavorite)

            // Then
            // Verify that updateFavSerie was called with the correct parameters
            coEvery { dataSource.updateFavSerie(id, isFavorite) }
        }
    }

    @Test
    fun `insertAllSeries inserts series into local data source`() { runBlocking {
        // Given -> Object mocked
        val series = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )

        // Configuring the mock to return void
        coEvery { repository.insertAllSeries(series) } returns Unit

        // When -> Calling to the method that is going to be tested
        repository.insertAllSeries(series)

        // Then
        // Verify that insertAllSeries was called with the correct parameter
        coEvery { dataSource.insertAllSeries(series.modelToSerieListEntity()) }
        }
    }


    @Test
    fun `getAllSeries retrieves all series from local data source`() = runBlocking {
        // Given -> Object mocked
        val expectedSeries = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )

        // Configuring the mock to return a entity list
        coEvery { dataSource.getAllSeries() } returns expectedSeries.modelToSerieListEntity()

        // When -> Calling to the method that is going to be tested
        val result = repository.getAllSeries()

        // Then
        // Verify that actual response and expected response are equals
        assertEquals(expectedSeries, result)
    }
}