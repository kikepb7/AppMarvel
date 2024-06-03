package com.enriquepalmadev.domain_layer.feature.series.usecase

import com.enriquepalmadev.data_layer.feature.series.repository.FilmSerieRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before

class FetchListFilterByNameUseCaseTest {

    @RelaxedMockK
    private lateinit var filmSerieRepositoryImpl: FilmSerieRepositoryImpl

    lateinit var fetchListFilterByNameUseCase: FetchListFilterByNameUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        //fetchListFilterByNameUseCase = FetchListFilterByNameUseCase(filmSerieRepositoryImpl)
    }
}