package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertAllSeriesUseCaseTest {

    private lateinit var repository: ILocalSerieRepository
    private lateinit var useCase: InsertAllSeriesUseCase

    // This function is going to be executed before every @Test
    @Before
    fun onBefore(){
        repository = mockk()
        useCase = InsertAllSeriesUseCase(repository)
    }

    @Test
    fun `insertAllSeries inserts series into local repository`() {
        runBlocking {
            // Given -> Preparing the initial state of the test
            val series = listOf(
                FilmSerieModel(1, "Series One", "", "", "", 2020, false),
                FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
                FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
            )

            // Configuring the mock to return the response object mocked
            coEvery { repository.insertAllSeries(series) } returns Unit

            // When -> Calling to the method that is going to be tested
            useCase.insertAllSeries(series)

            // Then
            // Verify that insertAllSeries was called with the correct parameter
            coEvery { repository.insertAllSeries(series) }
        }
    }
}