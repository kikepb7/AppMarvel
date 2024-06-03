package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.data_layer.feature.series.repository.FilmSerieRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchListOfAllSeriesUseCaseTest {

    @RelaxedMockK
    private lateinit var filmSerieRepositoryImpl: FilmSerieRepositoryImpl

    lateinit var fetchListOfAllSeriesUseCase: FetchListOfAllSeriesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        fetchListOfAllSeriesUseCase = FetchListOfAllSeriesUseCase(filmSerieRepositoryImpl)
    }

    @Test
    fun whenTheApiDoesntReturnAnythingThenError() = runBlocking {
        // Given
        // coEvery { filmSerieRepositoryImpl.getListOfAllSeries() } returns emptyList()

        // When

        // Then

    }
}