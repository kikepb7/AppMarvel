package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FetchListOfSeriesOrderByAlphabetUseCaseTest {

    @Test
    fun `getListOfSeriesOrderByAlphabet returns success when series is not empty`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val series = listOf(
            FilmSerieModel(1, "Series B", "", "", "", 2020, false),
            FilmSerieModel(2, "Series A", "", "", "", 2021, false),
            FilmSerieModel(3, "Series C", "", "", "", 2022, false)
        )
        val sortedSeries = listOf(series[1], series[0], series[2]) // Sorted by title alphabetically
        val useCase = FetchListOfSeriesOrderByAlphabetUseCase()

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListOfSeriesOrderByAlphabet(series).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Success type
        assertTrue(result.first() is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(sortedSeries, (result.first() as Either.Success).data)
    }

    @Test
    fun `getListOfSeriesOrderByAlphabet returns error when series is empty`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val series = emptyList<FilmSerieModel>()
        val useCase = FetchListOfSeriesOrderByAlphabetUseCase()

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListOfSeriesOrderByAlphabet(series).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Error type
        assertTrue(result.first() is Either.Error)
        // Verify that result.first() as Either.Error type is an EmptyErrorDomain
        assertTrue((result.first() as Either.Error).error is FailureDomain.EmptyErrorDomain)
    }
}