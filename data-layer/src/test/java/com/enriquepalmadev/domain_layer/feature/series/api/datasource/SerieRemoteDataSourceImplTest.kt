package com.enriquepalmadev.domain_layer.feature.series.api.datasource

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.dtos.DataDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelCharactersDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelComicsDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelCreatorsDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelEventsDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelStoriesDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelThumbnailDto
import com.enriquepalmadev.data_layer.feature.series.api.dtos.ObjectResponseDto
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.domain_layer.commons.Either
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class SerieRemoteDataSourceImplTest {

    private lateinit var api: IMarvelFilmSerieService
    private lateinit var dataSource: SerieRemoteDataSourceImpl

    // This function is going to be executed before every @Test
    @Before
    fun onBefore() {
        api = mockk()
        dataSource = SerieRemoteDataSourceImpl(api)
    }

    @Test
    fun `getListOfAllSeries returns success when response is successful`() = runBlocking {

        // Object mocked
        val marvelSeriesItem = MarvelFilmSerieItemDto(
            characters = MarvelCharactersDto(0, "", emptyList(), 0),
            comics = MarvelComicsDto(0, "", emptyList(), 0),
            creators = MarvelCreatorsDto(0, "", emptyList(), 0),
            description = "description",
            endYear = 2024,
            events = MarvelEventsDto(0, "", emptyList(), 0),
            id = 1,
            modified = "modified",
            next = Any(),
            previous = Any(),
            rating = "rating",
            resourceURI = "resourceURI",
            startYear = 2020,
            stories = MarvelStoriesDto(0, "", emptyList(), 0),
            thumbnail = MarvelThumbnailDto("jpg", "path"),
            title = "title",
            type = "type",
            urls = emptyList()
        )

        val responseDto = ObjectResponseDto(
            code = 200,
            data = DataDto(
                count = 1,
                limit = 20,
                offset = 0,
                results = listOf(marvelSeriesItem),
                total = 1
            )
        )

        // Configuring the mock to return success
        coEvery { api.getListOfAllSeries() } returns Response.success(responseDto)

        // Calling to the method that is going to be tested
        val result = dataSource.getListOfAllSeries()

        // Verify that result is Either.Success type
        assertTrue(result is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(responseDto, (result as Either.Success).data)
    }

    @Test
    fun `getListOfAllSeries returns UnauthorizedErrorData when response code is 401`() = runBlocking {

        // Configuring the mock to return a 401
        coEvery { api.getListOfAllSeries() } returns Response.error(Constants.ERROR_401, "".toResponseBody(null))

        // Calling to the method that is going to be tested
        val result = dataSource.getListOfAllSeries()

        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Verify that actual response and expected response are equals
        assertEquals(UnauthorizedErrorData, (result as Either.Error).error)
    }

    @Test
    fun `getListOfAllSeries returns CustomErrorData when response code is not 401`() = runBlocking {

        // Mocking an error code
        val errorCode = 500

        // Configuring the mock to return the mock error
        coEvery { api.getListOfAllSeries() } returns Response.error(errorCode,
            "Server error".toResponseBody(null)
        )

        // Calling to the method that is going to be tested
        val result = dataSource.getListOfAllSeries()

        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Casting the error to CustomErrorData
        val error = (result as Either.Error).error as CustomErrorData
        // Verify that actual response and expected response are equals
        assertEquals(errorCode.toString(), error.code)
    }

    @Test
    fun `getListOfAllSeries returns UnknownHostErrorData when UnknownHostException is thrown`() = runBlocking {

        // Configuring the mock to throw the UnknownHostException
        coEvery { api.getListOfAllSeries() } throws UnknownHostException()

        // Calling to the method that is going to be tested
        val result = dataSource.getListOfAllSeries()

        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Verify that actual response and expected response are equals
        assertEquals(UnknownHostErrorData, (result as Either.Error).error)
    }

    @Test
    fun `getSerieById returns success when response is successful and has result`() = runBlocking {

        // Object mocked
        val marvelItem = MarvelFilmSerieItemDto(
            characters = MarvelCharactersDto(0, "", emptyList(), 0),
            comics = MarvelComicsDto(0, "", emptyList(), 0),
            creators = MarvelCreatorsDto(0, "", emptyList(), 0),
            description = "description",
            endYear = 2024,
            events = MarvelEventsDto(0, "", emptyList(), 0),
            id = 1,
            modified = "modified",
            next = Any(),
            previous = Any(),
            rating = "rating",
            resourceURI = "resourceURI",
            startYear = 2020,
            stories = MarvelStoriesDto(0, "", emptyList(), 0),
            thumbnail = MarvelThumbnailDto("jpg", "path"),
            title = "title",
            type = "type",
            urls = emptyList()
        )

        val responseDto = ObjectResponseDto(
            code = 200,
            data = DataDto(
                count = 1,
                limit = 20,
                offset = 0,
                results = listOf(marvelItem),
                total = 1
            )
        )

        // Configuring the mock to return success
        coEvery { api.getSerieById(1) } returns Response.success(responseDto)

        // Calling to the method that is going to be tested
        val result = dataSource.getSerieById(1)

        // Verify that result is Either.Success type
        assertTrue(result is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(marvelItem, (result as Either.Success).data)
    }

    @Test
    fun `getSerieById returns CustomErrorData when response is successful but has no result`() = runBlocking {

        // Object mocked
        val responseDto = ObjectResponseDto(
            code = 200,
            data = DataDto(
                count = 1,
                limit = 20,
                offset = 0,
                results = emptyList(),
                total = 1
            )
        )

        // Configuring the mock to return success
        coEvery { api.getSerieById(1) } returns Response.success(responseDto)

        // Calling to the method that is going to be tested
        val result = dataSource.getSerieById(1)

        // Verify that result is Either.Success type
        assertTrue(result is Either.Error)
        // Casting the error to CustomErrorData
        val error = (result as Either.Error).error as CustomErrorData
        // Verify that actual response and expected response are equals
        assertEquals("java.util.NoSuchElementException: List is empty.", error.code)
    }

    @Test
    fun `getSerieById returns CustomErrorData when response is not successful`() = runBlocking {

        // Mocking an error code
        val errorCode = 404

        // Configuring the mock to return the mock error
        coEvery { api.getSerieById(1) } returns Response.error(errorCode,
            "Not found".toResponseBody(null)
        )

        // Calling to the method that is going to be tested
        val result = dataSource.getSerieById(1)

        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Casting the error to CustomErrorData
        val error = (result as Either.Error).error as CustomErrorData
        // Verify that actual response and expected response are equals
        assertEquals(errorCode.toString(), error.code)
    }

    @Test
    fun `getSerieById returns UnknownHostErrorData when UnknownHostException is thrown`() = runBlocking {

        // Configuring the mock to throw the UnknownHostException
        coEvery { api.getSerieById(1) } throws UnknownHostException()

        // Calling to the method that is going to be tested
        val result = dataSource.getSerieById(1)

        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Verify that actual response and expected response are equals
        assertEquals(UnknownHostErrorData, (result as Either.Error).error)
    }
}
