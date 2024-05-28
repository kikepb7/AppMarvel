package com.enriquepalmadev.ui_layer.feature.comics.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.Either
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.domain_layer.feature.comics.usecase.FetchComicUseCase
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenEmpty
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenError
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenLoading
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicScreenState
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.ComicListType
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModel
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.toComicListModelHeader
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ComicsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val fetchComicUseCase = mockk<FetchComicUseCase>()
    private lateinit var viewModel: ComicsViewModel
    private val comicListComicModelMocked = listOf(
        ComicModel(
            id = 1,
            title = "Spiderman",
            description = "Spiderman",
            pageCount = 30,
            thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
        ), ComicModel(
            id = 1,
            title = "Spiderman",
            description = "Spiderman",
            pageCount = 30,
            thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
        )
    )


    private val comicListScreenEmptyMocked = ComicListScreenEmpty(
        image = R.drawable.spiderman_deadpool_empty_list,
        emptyMessage = R.string.empty_list
    )

    private val errorMocked = ComicListScreenError(
        image = R.drawable.comic_detail_error,
        errorMsg = R.string.api_error
    )

    @Before
    fun setUp() {
        viewModel = ComicsViewModel(fetchComicListUseCase = fetchComicUseCase)
        Dispatchers.setMain(dispatcher = StandardTestDispatcher())  // TODO --> Es lo que he encontrado para que funcione, debo dejarlo?
    }

    @Test
    fun `GIVEN fetch comic list WHEN we call fetchComicListUseCase method and there's an service error THEN it return a failure object`() {
        return runTest {

            coEvery { fetchComicUseCase.fetchComicList() } returns flow {
                emit(
                    Either.Failure(
                        FailureDomain.ApiError
                    )
                )
            }

            viewModel.state.test {
                // First State
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false)
                    ), awaitItem()
                )

                viewModel.getComicsList()

                // Updated state
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = true)
                    ), awaitItem()
                )

                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false),
                        errorScreenData = errorMocked
                    ), awaitItem()
                )

                // It finish to emit
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    // TODO --> Tengo el catch en el viewModel, pero el caso de uso no contempla devolver una Exception
    fun `GIVEN fetch comic list WHEN we call fetchComicListUseCase method and there's an exception THEN it return a throwable error`() {
        return runTest {
            val error = ComicListScreenError(
                image = 2131230828,
                errorMsg = 2132017182
            )

//            coEvery { fetchComicUseCase.fetchComicList() } returns flow (emit { Exception() })
            coEvery { fetchComicUseCase.fetchComicList() } throws Exception()

            viewModel.state.test {
                // First State
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false)
                    ), awaitItem()
                )

                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = true)
                    ), awaitItem()
                )

                viewModel.getComicsList()

                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false),
                        errorScreenData = error
                    ), awaitItem()
                )

                // It finish to emit
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `GIVEN fetch comic list WHEN we call fetchComicListUseCase method and the response is success THEN it return an empty comic list`() {

        return runTest {
            coEvery { fetchComicUseCase.fetchComicList() } returns flow {
                emit(
                    Either.Success(
                        data = emptyList()  // Si pasamos una lista de comics da error
                    )
                )
            }

            viewModel.state.test {
                // First State
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false)
                    ), awaitItem()
                )

                viewModel.getComicsList()

                // Updated state
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = true)
                    ), awaitItem()
                )

                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false),
                        emptyListScreenData = comicListScreenEmptyMocked
                    ), awaitItem()
                )

                // It finish to emit
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `GIVEN fetch comic list WHEN we call fetchComicListUseCase method and the response is success THEN it return a comic list`() {
        return runTest {
            coEvery { fetchComicUseCase.fetchComicList() } returns flow {
                emit(
                    Either.Success(
                        data = comicListComicModelMocked    // Si pasamos un emptyList() debe dar error
                    )
                )
            }

            viewModel.state.test {
                // First State
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false)
                    ), awaitItem()
                )

                viewModel.getComicsList()

                // Updated state
                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = true)
                    ), awaitItem()
                )

                assertEquals(
                    ComicScreenState(
                        loadingScreenData = ComicListScreenLoading(loader = false),
                        comicScreenData = ComicListScreenModel(
                            comicListScreenHeader = toComicListModelHeader(),
                            comicListModel = comicListComicModelMocked.toComicListModel(
                                ComicListType.ALL_COMICS    // Si cambio a FAVORITES, debe dar error
                            ),
                            favoriteListModel = comicListComicModelMocked.toComicListModel(
                                ComicListType.FAVORITES
                            )
                        )
                    ), awaitItem()
                )

                // It finish to emit
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}