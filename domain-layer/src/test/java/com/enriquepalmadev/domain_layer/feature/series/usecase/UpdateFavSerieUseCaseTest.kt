package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateFavSerieUseCaseTest {

    @Test
    fun `updateFavSerie updates favorite status in local repository`() { runBlocking {
        // Given
        val id = 1
        val isFavorite = true
        val localSerieRepository: ILocalSerieRepository = mockk {
            coEvery { updateFavSerie(id, isFavorite) } returns Unit
        }
        val useCase = UpdateFavSerieUseCase(localSerieRepository)

        // When
        useCase.updateFavSerie(id, isFavorite)

        // Then
        // Verify that updateFavSerie was called with the correct parameters
        coEvery { localSerieRepository.updateFavSerie(id, isFavorite) }
        }
    }
}