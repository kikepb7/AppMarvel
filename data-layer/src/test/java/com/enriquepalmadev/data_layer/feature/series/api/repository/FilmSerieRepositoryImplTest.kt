package com.enriquepalmadev.data_layer.feature.series.api.repository

import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.dtos.*
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.mapper.IFilmSerieMapper
import com.enriquepalmadev.data_layer.feature.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FilmSerieRepositoryImplTest {

    private lateinit var dataSource: SerieRemoteDataSourceImpl
    private lateinit var repository: FilmSerieRepositoryImpl
    private val mapper: IFilmSerieMapper = mockk()

    // This function is going to be executed before every @Test
    @Before
    fun onBefore() {
        dataSource = mockk()
        repository = FilmSerieRepositoryImpl(dataSource)
    }

    @Test
    fun `getListOfAllSeries returns success when response is successful`() = runBlocking {

        // Given -> Object mocked
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

        // Configuring the mock to return success with the response object mocked
        coEvery { dataSource.getListOfAllSeries() } returns Either.Success(responseDto)
        // Configuring the mock to mapping the response object mocked
        coEvery { mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelSeriesItem) } returns FilmSerieModel(
            id = 1,
            title = "title",
            description = "description",
            thumbnailPath = "path",
            thumbnailExt = "jpg",
            startYear = 2020,
            isFav = false
        )

        // When -> Calling to the method that is going to be tested
        val result = repository.getListOfAllSeries()

        // Then
        // Verify that result is Either.Success type
        assertTrue(result is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals(1, (result as Either.Success).data?.size)
    }

    @Test
    fun `getListOfAllSeries returns error when response is error`() = runBlocking {

        // Given
        // Configuring the mock to return an CustomErrorData
        coEvery { dataSource.getListOfAllSeries() } returns Either.Error(CustomErrorData("Error", "error message"))

        // When -> Calling to the method that is going to be tested
        val result = repository.getListOfAllSeries()

        // Then
        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Verify that actual response and expected response are equals
        assertTrue((result as Either.Error).error is FailureDomain.CustomErrorDomain)
    }

    @Test
    fun `getSerieById returns success when response is successful`() = runBlocking {

        // Given -> Object mocked
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

        // Mocking the response
        val responseDto = Either.Success(marvelSeriesItem)

        // Configuring the mock to return the response mocked before
        coEvery { dataSource.getSerieById(1) } returns responseDto
        // Configuring the mock to mapping the response object mocked
        coEvery { mapper.marvelFilmSerieItemDtoToFilmSerieModel(marvelSeriesItem) } returns FilmSerieModel(
            id = 1,
            title = "title",
            description = "description",
            thumbnailPath = "path",
            thumbnailExt = "jpg",
            startYear = 2020,
            isFav = false
        )

        // When -> Calling to the method that is going to be tested
        val result = repository.getSerieById(1)

        // Then
        // Verify that result is Either.Success type
        assertTrue(result is Either.Success)
        // Verify that actual response and expected response are equals
        assertEquals("title", (result as Either.Success).data.title)
    }

    @Test
    fun `getSerieById returns error when response is error`() = runBlocking {

        // Given
        // Configuring the mock to return an CustomErrorData
        coEvery { dataSource.getSerieById(1) } returns Either.Error(CustomErrorData("Error", "error message"))

        // When -> Calling to the method that is going to be tested
        val result = repository.getSerieById(1)

        // Then
        // Verify that result is Either.Error type
        assertTrue(result is Either.Error)
        // Verify that actual response and expected response are equals
        assertTrue((result as Either.Error).error is FailureDomain.CustomErrorDomain)
    }
}
