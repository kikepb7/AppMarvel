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
import org.junit.Before
import org.junit.Test

class FetchListOfAllSeriesUseCaseTest {

    private lateinit var repository: IFilmSerieRepository
    private lateinit var useCase: FetchListOfAllSeriesUseCase

    // This function is going to be executed before every @Test
    @Before
    fun onBefore(){
        repository = mockk()
        useCase = FetchListOfAllSeriesUseCase(repository)
    }

    @Test
    fun `getListOfAllSeries returns success when repository response is success`() = runBlocking {
        // Given -> Object mocked
        val series = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "desc", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )
        val sortedSeries = listOf(series[1], series[0], series[2]) // Sorted by startYear descending

        // Configuring the mock to return success
        coEvery { repository.getListOfAllSeries() } returns Either.Success(series)

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListOfAllSeries().toList()

        // Then
        // Verify that result.size is 1
        assertTrue(result.size == 1)
        // Verify that result is Either.Success type
        assertTrue(result.first() is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(sortedSeries.first().startYear, (result.first() as Either.Success).data?.first()?.startYear)
    }

    @Test
    fun `getListOfAllSeries returns error when repository response is error`() = runBlocking {
        // Given -> Object mocked
        val error = FailureDomain.UnknownHostErrorDomain

        // Configuring the mock to return error with the error mocked
        coEvery { repository.getListOfAllSeries() } returns Either.Error(error)

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListOfAllSeries().toList()

        // Then
        // Verify that result.size is 1
        assertTrue(result.size == 1)
        // Verify that result is Either.Error type
        assertTrue(result.first() is Either.Error)
        // Verify that actual response and expected response are equals
        assertEquals(error, (result.first() as Either.Error).error)
    }
}