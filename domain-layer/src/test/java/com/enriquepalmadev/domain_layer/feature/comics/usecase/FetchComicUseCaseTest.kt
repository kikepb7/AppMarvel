package com.enriquepalmadev.domain_layer.feature.comics.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FetchComicUseCaseTest {


    private val comicRepository = mockk<ComicRepository>()
    private lateinit var fetchComicUseCase: FetchComicUseCase
    private fun getComicListMocked() = listOf(
        ComicModel(
            id = 1,
            title = "Spider-man",
            description = "Spider-man comic",
            pageCount = 2,
            thumbnail = "https://example.com"
        ),
        ComicModel(
            id = 2,
            title = "Ironman",
            description = "Ironman comic",
            pageCount = 2,
            thumbnail = "https://example.com"
        )
    )

    @Before
    fun setUp() {
        fetchComicUseCase = FetchComicUseCase(comicRepository)
    }

    @Test
    fun `GIVEN a mocked repository WHEN fetching comic list THEN repository is called once and returns expected result`() =
        runTest {

            // GIVEN
            coEvery { comicRepository.fetchComicList() } returns Either.Success(getComicListMocked())

            // WHEN
            val result = fetchComicUseCase.fetchComicList().first()

            // THEN
            coVerify(exactly = 1) { comicRepository.fetchComicList() }
            assertEquals(result, Either.Success(getComicListMocked()))
        }
}