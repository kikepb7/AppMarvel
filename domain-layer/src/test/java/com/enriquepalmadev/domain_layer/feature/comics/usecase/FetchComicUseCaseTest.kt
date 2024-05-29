package com.enriquepalmadev.domain_layer.feature.comics.usecase

import com.enriquepalmadev.domain_layer.commons.Either
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchComicUseCaseTest {

    private val comicRepository = mockk<ComicRepository>()
    private lateinit var fecthComicUseCase: FetchComicUseCase

    @Before
    fun setUp() {
        fecthComicUseCase = FetchComicUseCase(comicRepository)
        Dispatchers.setMain(dispatcher = StandardTestDispatcher())
    }

    @Test
    fun `check if repository is called`() = runTest {

        // Given
//        coEvery { comicRepository.fetchComicList() } returns Either.Success(getComicListMocked())
        coEvery { comicRepository.fetchComicList() } answers { Either.Success(getComicListMocked()) }

        // When
//        fecthComicUseCase.fetchComicList()
        fecthComicUseCase.fetchComicList().collect { }


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