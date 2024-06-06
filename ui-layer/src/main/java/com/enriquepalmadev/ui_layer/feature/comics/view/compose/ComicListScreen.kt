package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenHeader
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenItemModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenTitle
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicScreenState
import com.enriquepalmadev.ui_layer.feature.comics.view.model.SearchBarHeader

@Composable
fun ComicListScreen(
    state: ComicScreenState,
    onComicClicked: (ComicModel) -> Unit,
    onBackButtonClicked: () -> Unit,
    onSearchBar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black),
    ) {

        if (state.loadingScreenData.loader) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingScreen()
            }
        }

        state.errorScreenData?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorScreen(comicListScreenError = state.errorScreenData)
            }
        }

        state.comicScreenData?.let { comicScreenModel ->

            HeaderComicList(
                comicListScreenHeader = comicScreenModel.comicListScreenHeader,
                onBackButtonClicked = { onBackButtonClicked() },
                onSearchBar = { text ->
                    onSearchBar(text)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ComicList(
                comicListScreenTitle = comicScreenModel.comicListModel?.comicListScreenTitle,
                comicListScreenItemModel = comicScreenModel.comicListModel?.comicsModel,
                onComicClicked = { comic ->
                    onComicClicked(comic)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            ComicList(
                comicListScreenTitle = comicScreenModel.favoriteListModel?.comicListScreenTitle,
                comicListScreenItemModel = comicScreenModel.favoriteListModel?.comicsModel,
                onComicClicked = { comic ->
                    onComicClicked(comic)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComicListScreenPreview() {
    ComicListScreen(
        state = ComicScreenState(
            comicScreenData = ComicListScreenModel(
                comicListScreenHeader = ComicListScreenHeader(
                    imageLogo = R.drawable.marvel_comics_logo,
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    placeholderText = R.string.search_text,
                    searchBarHeader = SearchBarHeader(placeholder = R.string.search_text)
                ),
                comicListModel = ComicListModel(
                    comicListScreenTitle = ComicListScreenTitle(
                        icon = R.drawable.ironman,
                        title = R.string.all_comics_title
                    ),
                    comicsModel = listOf(
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        ),
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        ),
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        )
                    )
                ),
                favoriteListModel = ComicListModel(
                    comicListScreenTitle = ComicListScreenTitle(
                        icon = R.drawable.ic_solid_heart,
                        title = R.string.favorite_comics_title
                    ),
                    comicsModel = listOf(
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        ),
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        ),
                        ComicListScreenItemModel(
                            comic = ComicModel(
                                id = 1,
                                title = "Spiderman",
                                description = "Spiderman",
                                pageCount = 30,
                                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/9/b0/62f3c7bba6677.jpg"
                            )
                        )
                    )
                )
            )
        ),
        {},
        {},
        {}
    )
}