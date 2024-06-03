package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FetchListFilterByNameUseCaseTest {

    private val useCase = FetchListFilterByNameUseCase()

    @Test
    fun `getListFilterByName returns success when series is not empty and matches searchText`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val series = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )
        val searchText = "one"

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListFilterByName(searchText, series).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Success type
        assertTrue(result.first() is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(1, (result.first() as Either.Success).data.size)
        // Verify that actual response and expected response are equals
        assertEquals("Series One", (result.first() as Either.Success).data.first().title)
    }

    @Test
    fun `getListFilterByName returns error when series is empty`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val series = emptyList<FilmSerieModel>()
        val searchText = "hello i'm testing"

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListFilterByName(searchText, series).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Error type
        assertTrue(result.first() is Either.Error)
        // Verify that result.first() as Either.Error type is an EmptyErrorDomain
        assertTrue((result.first() as Either.Error).error is FailureDomain.EmptyErrorDomain)
    }

    @Test
    fun `getListFilterByName returns success when series is not empty but no match found`() = runBlocking {
        // Given -> Preparing the initial state of the test
        val series = listOf(
            FilmSerieModel(1, "Series One", "", "", "", 2020, false),
            FilmSerieModel(2, "Series Two", "", "", "", 2021, false),
            FilmSerieModel(3, "Series Three", "", "", "", 2022, false)
        )
        val searchText = "four"

        // When -> Calling to the method that is going to be tested
        val result = useCase.getListFilterByName(searchText, series).toList()

        // Then
        // Verify that result.size == 1
        assertTrue(result.size == 1)
        // Verify that result.first() is Either.Success type
        assertTrue(result.first() is Either.Success)
        // Verify that result.first() as Either.Success type but data is empty
        assertTrue((result.first() as Either.Success).data.isEmpty())
    }
}