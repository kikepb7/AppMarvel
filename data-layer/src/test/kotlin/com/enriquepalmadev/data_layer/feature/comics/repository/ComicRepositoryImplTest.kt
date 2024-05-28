package com.enriquepalmadev.data_layer.feature.comics.repository

import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.dto.DataDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicRepositoryImplTest {

    @MockK
    private lateinit var mockDataSource: ComicRemoteDataSource
    private lateinit var comicRepository: ComicRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        comicRepository = ComicRepositoryImpl(mockDataSource)
    }

    @Test
    fun `when 'fetchComicList' is successful, response is a list of comic list`() = runTest {

        // Given
        coEvery { mockDataSource.fetchComicsFromApi() } returns Either.Success(
            data = getComicModel()
        )

        // When
        val response = comicRepository.fetchComicList()

        // Then
        assert(response is Either.Success)
        coVerify(exactly = 1) { mockDataSource.fetchComicsFromApi() }
    }

    private fun getComicModel() = ResponseMarvelDto(
        code = 1,
        data = DataDto(),
        etag = "none"
    )
}