package com.enriquepalmadev.data_layer.feature.comics.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.dto.ComicDto
import com.enriquepalmadev.data_layer.feature.comics.dto.DataDto
import com.enriquepalmadev.data_layer.feature.comics.dto.FailureDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ThumnailDto
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ComicRepositoryImplTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val comicRemoteDataSource = mockk<ComicRemoteDataSource>()
    private lateinit var comicRepositoryImpl: ComicRepositoryImpl
    private val getResponseMarvelDto =
        ResponseMarvelDto(
            code = 1,
            data = DataDto(
                results = listOf(
                    ComicDto(
                        id = 1,
                        title = "Test Comic",
                        description = "A test comic",
                        thumbnail = ThumnailDto(path = "path", extension = "jpg"),
                    )
                )
            ),
            etag = "none"
        )

    @Before
    fun setUp() {
        comicRepositoryImpl = ComicRepositoryImpl(comicRemoteDataSource)
        Dispatchers.setMain(dispatcher = StandardTestDispatcher())
    }

    @Test
    fun `WHEN 'fetchComicList' is Successful, response is a list of comic list`() = runTest {

        // Given
        coEvery { comicRemoteDataSource.fetchComicsFromApi() } returns Either.Success(
            getResponseMarvelDto
        )

        // When
        val response = comicRepositoryImpl.fetchComicList()

        // Then
        assert(response is Either.Success<List<ComicModel>?>)
        coVerify(exactly = 1) { comicRemoteDataSource.fetchComicsFromApi() }
    }

    @Test
    fun `WHEN 'fetchComicList' is Successful, response is a null list of comic model`() = runTest {

        // Given
        coEvery { comicRemoteDataSource.fetchComicsFromApi() } returns Either.Success(
            getResponseMarvelDto.copy(
                data = DataDto(
                    results = null
                )
            )
        )

        // When
        val response = comicRepositoryImpl.fetchComicList()

        // Then
        assert(response is Either.Success<List<ComicModel>?>)
        coVerify(exactly = 1) { comicRemoteDataSource.fetchComicsFromApi() }
    }

    @Test
    fun `WHEN 'fetchComicList' is Error, response is a FailureDomain error`() = runTest {

        // Given
        coEvery { comicRemoteDataSource.fetchComicsFromApi() } returns Either.Error(
            FailureDto(code = -1, message = "")
        )

        // When
        val response = comicRepositoryImpl.fetchComicList()

        // Then
        assert(response is Either.Error<FailureDomain>)
        coVerify(exactly = 1) { comicRemoteDataSource.fetchComicsFromApi() }
    }
}
