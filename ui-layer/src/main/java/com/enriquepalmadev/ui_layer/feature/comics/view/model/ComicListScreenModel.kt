package com.enriquepalmadev.ui_layer.feature.comics.view.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel

data class ComicListScreenTitle(
    val icon: Int,
    val title: Int
)

data class ComicListScreenItemModel(
    val comic: ComicModel
)

data class ComicListScreenHeader(
    val imageLogo: Int,
    val icon: ImageVector,
    val placeholderText: Int,
    val searchBarHeader: SearchBarHeader
)

data class SearchBarHeader(
    val placeholder: Int,
)

data class ComicListScreenLoading(
    val loader: Boolean
)

data class ComicListScreenError(
    val image: Int,
    val errorMsg: Int
)

data class ComicListScreenEmpty(
    val image: Int,
    val emptyMessage: Int
)

data class ComicListModel(
    val comicListScreenTitle: ComicListScreenTitle,
    val comicsModel: List<ComicListScreenItemModel>
)

data class ComicListScreenModel(
    val comicListScreenHeader: ComicListScreenHeader,
    val comicListModel: ComicListModel? = null,
    val favoriteListModel: ComicListModel? = null,
)

data class ComicScreenState(
    val comicScreenData: ComicListScreenModel? = null,
    val loadingScreenData: ComicListScreenLoading = ComicListScreenLoading(loader = false),
    val errorScreenData: ComicListScreenError? = null,
    val emptyListScreenData: ComicListScreenEmpty? = null
)