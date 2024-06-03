package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FetchListOfAllSeriesUseCaseTest {

    @Test
    fun `getListOfAllSeries returns success when repository response is success`() = runBlocking {
        // Given
        val series = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "desc", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )
        val sortedSeries = listOf(series[1], series[0], series[2]) // Sorted by startYear descending
        val repository: IFilmSerieRepository = mockk {
            coEvery { getListOfAllSeries() } returns Either.Success(series)
        }
        val useCase = FetchListOfAllSeriesUseCase(repository)

        // When
        val result = useCase.getListOfAllSeries().toList()

        // Then
        assertTrue(result.size == 1)
        assertTrue(result.first() is Either.Success)
        assertEquals(sortedSeries.first().startYear, (result.first() as Either.Success).data?.first()?.startYear)
    }

    @Test
    fun `getListOfAllSeries returns error when repository response is error`() = runBlocking {
        // Given
        val error = FailureDomain.UnknownHostErrorDomain
        val repository: IFilmSerieRepository = mockk {
            coEvery { getListOfAllSeries() } returns Either.Error(error)
        }
        val useCase = FetchListOfAllSeriesUseCase(repository)

        // When
        val result = useCase.getListOfAllSeries().toList()

        // Then
        assertTrue(result.size == 1)
        assertTrue(result.first() is Either.Error)
        assertEquals(error, (result.first() as Either.Error).error)
    }
}