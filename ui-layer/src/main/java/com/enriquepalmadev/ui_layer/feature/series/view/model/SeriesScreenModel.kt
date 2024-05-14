package com.enriquepalmadev.ui_layer.feature.series.view.model

import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel

data class SeriesItem (
    val series : FilmSerieModel,
    val iconFavResource : Int
)

data class SeriesBody (
    val seriesList: List<SeriesItem>
)

data class SeriesHeader (
    val buttonTextResource: Int
    // val placeholderSearchViewResource : Int
)

data class SeriesScreen (
    val header: SeriesHeader,
    val list : SeriesBody
)

data class ErrorScreen (
    val errorImageResource: Int,
    val errorTitleResource : Int,
    val errorDescriptionResource : Int
)

data class LoadingScreen (
    val placeholderResource : Int
)

data class State (
    val seriesScreen : SeriesScreen,
    val errorScreen : ErrorScreen,
    val loadingScreen : LoadingScreen
)


/*
sealed class FilmSerieUIState {
    data object Idle : FilmSerieUIState()
    data class Error (val error: FailureDomain) : FilmSerieUIState()
    data class Loading(val placeholder: String) : FilmSerieUIState()
    data class ListReceived (val list: List<FilmSerieModel>) : FilmSerieUIState()
}
 */