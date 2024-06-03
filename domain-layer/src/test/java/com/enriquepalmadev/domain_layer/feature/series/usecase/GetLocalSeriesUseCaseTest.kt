package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetLocalSeriesUseCaseTest {

    @Test
    fun `getAllSeries returns list of series from local repository`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val expectedSeries = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )
        val localSerieRepository: ILocalSerieRepository = mockk {
            coEvery { getAllSeries() } returns expectedSeries
        }
        val useCase = GetLocalSeriesUseCase(localSerieRepository)

        // When -> Calling to the method that is going to be tested
        val result = useCase.getAllSeries()

        // Then
        // Verify that actual response and expected response are equals
        assertEquals(expectedSeries, result)
    }
}