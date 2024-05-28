package com.enriquepalmadev.domain_layer.feature.comics.usecase

import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchComicUseCaseTest {

    @MockK
    private lateinit var comicRepository: ComicRepository
    private lateinit var fecthComicUseCase: FetchComicUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        comicRepository = mockk()
        fecthComicUseCase = FetchComicUseCase(comicRepository)
    }

    @Test
    fun `check if repository is called`() = runBlocking {

        // Given
        coEvery { comicRepository.fetchComicList() } returns Either.Success(getComicListMocked())

        // When
        fecthComicUseCase.fetchComicList().first()

        // Then
        coVerify(exactly = 1) { comicRepository.fetchComicList() }
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