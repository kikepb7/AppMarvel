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

class FetchSerieByIdUseCaseTest {

    @Test
    fun `getSerieById returns success when repository response is success`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val filmSerieModel = FilmSerieModel(1, "Title", "Description", "", "", 2021, false)
        val repository: IFilmSerieRepository = mockk {
            coEvery { getSerieById(1) } returns Either.Success(filmSerieModel)
        }
        val useCase = FetchSerieByIdUseCase(repository)

        // When -> Calling to the method that is going to be tested
        val result = useCase.getSerieById(1).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Success type
        assertTrue(result.first() is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(filmSerieModel, (result.first() as Either.Success).data)
    }

    @Test
    fun `getSerieById returns error when repository response is error`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val error = FailureDomain.AnotherErrorDomain
        val repository: IFilmSerieRepository = mockk {
            coEvery { getSerieById(1) } returns Either.Error(error)
        }
        val useCase = FetchSerieByIdUseCase(repository)

        // When -> Calling to the method that is going to be tested
        val result = useCase.getSerieById(1).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Error type
        assertTrue(result.first() is Either.Error)
        // Verify that result.first() as Either.Error type is an EmptyErrorDomain
        assertEquals(error, (result.first() as Either.Error).error)
    }
}