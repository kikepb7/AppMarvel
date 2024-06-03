package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateFavSerieUseCaseTest {

    private lateinit var repository: ILocalSerieRepository
    private lateinit var useCase: UpdateFavSerieUseCase

    @Before
    fun onBefore(){
        repository = mockk()
        useCase = UpdateFavSerieUseCase(repository)
    }

    @Test
    fun `updateFavSerie updates favorite status in local repository`() { runBlocking {
        // Given -> Preparing the initial state of the test
        val id = 1
        val isFavorite = true

        // Configuring the mock to return void
        coEvery { repository.updateFavSerie(id, isFavorite) } returns Unit

        // When -> Calling to the method that is going to be tested
        useCase.updateFavSerie(id, isFavorite)

        // Then
        // Verify that updateFavSerie was called with the correct parameters
        coEvery { repository.updateFavSerie(id, isFavorite) }
        }
    }
}