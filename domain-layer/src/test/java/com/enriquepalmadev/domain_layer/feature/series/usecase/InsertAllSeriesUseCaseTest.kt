package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InsertAllSeriesUseCaseTest {

    @Test
    fun `insertAllSeries inserts series into local repository`() {
        runBlocking {
            // Given -> Preparing the initial state of the test
            val series = listOf(
                FilmSerieModel(1, "Series One", "", "", "", 2020, false),
                FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
                FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
            )
            val localSerieRepository: ILocalSerieRepository = mockk {
                coEvery { insertAllSeries(series) } returns Unit
            }
            val useCase = InsertAllSeriesUseCase(localSerieRepository)

            // When -> Calling to the method that is going to be tested
            useCase.insertAllSeries(series)

            // Then
            // Verify that insertAllSeries was called with the correct parameter
            coEvery { localSerieRepository.insertAllSeries(series) }
        }
    }
}