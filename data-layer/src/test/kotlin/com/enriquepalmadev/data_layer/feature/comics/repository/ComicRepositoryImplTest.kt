package com.enriquepalmadev.data_layer.feature.comics.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.dto.ComicDto
import com.enriquepalmadev.data_layer.feature.comics.dto.DataDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ResponseMarvelDto
import com.enriquepalmadev.data_layer.feature.comics.dto.ThumnailDto
import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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

    @Before
    fun setUp() {
        comicRepositoryImpl = ComicRepositoryImpl(comicRemoteDataSource)
        Dispatchers.setMain(dispatcher = StandardTestDispatcher())
    }

    @Test
    fun `when 'fetchComicList' is successful, response is a list of comic list`() = runTest {

        // Given
        val comicModelList = getComicModelList(getResponseMarvelDto())
        coEvery { comicRemoteDataSource.fetchComicsFromApi() } returns Either.Success(getResponseMarvelDto())

        // When
        val response = comicRepositoryImpl.fetchComicList()

        // Then
        coVerify(exactly = 1) { comicRemoteDataSource.fetchComicsFromApi() }
        assert(response is Either.Success<*>)
        val successResponse = response as Either.Success
        assert(successResponse.data == comicModelList)
    }

    private fun getResponseMarvelDto() = ResponseMarvelDto(
        code = 1,
        data = DataDto(results = listOf(
            ComicDto(
                id = 1,
                title = "Test Comic",
                description = "A test comic",
                thumbnail = ThumnailDto(path = "path", extension = "jpg"),
            ))),
        etag = "none"
    )

    private fun getComicModelList(responseMarvelDto: ResponseMarvelDto): List<ComicModel> {
        return responseMarvelDto.data?.results?.map {
            ComicModel(
                id = it.id,
                title = it.title ?: "",
                description = it.description ?: "",
                thumbnail = "${it.thumbnail?.path}.${it.thumbnail?.extension}",
                pageCount = it.pageCount ?: 0
            )
        } ?: emptyList()
    }
}