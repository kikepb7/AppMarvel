package com.enriquepalmadev.domain_layer.feature.comics.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FetchComicUseCaseTest {


    private val comicRepository = mockk<ComicRepository>()
    private lateinit var fetchComicUseCase: FetchComicUseCase

    @Before
    fun setUp() {
        fetchComicUseCase = FetchComicUseCase(comicRepository)
    }

    @Test
    fun `check if repository is called`() = runTest(UnconfinedTestDispatcher()) {

        // Given
        coEvery { comicRepository.fetchComicList() } returns Either.Success(getComicListMocked())

        // When
        val result = fetchComicUseCase.fetchComicList().first()

        // Then
        coVerify(exactly = 1) { comicRepository.fetchComicList() }
        assertEquals(result, Either.Success(getComicListMocked()))
    }

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
}