package com.enriquepalmadev.ui_layer.feature.comics.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicScreenState

@Composable
fun ComicListScreen(
    state: ComicScreenState,
    onComicClicked: (ComicModel) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (state.loadingScreenData.loader) {
            LoadingScreen()
        }

        state.errorScreenData?.let {
            ErrorScreen(comicListScreenError = state.errorScreenData)
        }

        state.comicScreenData?.let { comicScreenModel ->
            HeaderComicList(
                comicListScreenHeader = comicScreenModel.comicListScreenHeader,
                onBackButtonClicked = { onBackButtonClicked() }
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


/*@Preview(showBackground = true)
@Composable
fun ComicListScreenPreview() {
    ComicListScreen(
        state = ComicListScreenModel(
            comicListScreenHeader = ComicListScreenHeader(
                imageLogo = R.drawable.marvel_comics_logo,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                placeholderText = "Buscar",
                onClickSearch = {}
            ),
            comicListModel = ComicListModel(
                comicListScreenTitle = ComicListScreenTitle(
                    icon = R.drawable.ironman,
                    title = "TODOS LOS COMICS"
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
                    )
                )
            ),
            favoriteListModel = ComicListModel(
                comicListScreenTitle = ComicListScreenTitle(
                    icon = R.drawable.ironman,
                    title = "TODOS LOS COMICS"
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
                    )
                )
            ),
            comicListScreenLoading = ComicListScreenLoading(
                loader = false
            ),
            comicListScreenError = ComicListScreenError(
                image = R.drawable.unauthorized_error_logo,
                errorMsg = "Error 401"
            )
        )
    )
}*/

