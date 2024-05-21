package com.enriquepalmadev.ui_layer.feature.series.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.FilmSerieUIState

@Composable
fun FilmSerieFragmentCompose(
    uiState: FilmSerieUIState,
    dialogOrderBy: () -> Unit,
    itemClicked: (id: Int) -> Unit,
    favClicked: () -> Unit,
    onSearchQueryChange : (newText : String) -> Unit
) {

    if (uiState.isLoading){
        LoadingView()
    }

    if(uiState.list.isNotEmpty()){
        Series(
            series = uiState.list,
            dialogOrderBy = dialogOrderBy,
            itemClicked = itemClicked,
            favClicked = favClicked,
            onSearchQueryChange = onSearchQueryChange
        )
    }

    if(uiState.isError!=null){
        when(uiState.isError){
            is FailureDomain.CustomErrorDomain -> {
                ErrorView(
                    uiState.isError.code,
                    uiState.isError.msg,
                    R.drawable.groot_error
                )
            }

            FailureDomain.UnauthorizedErrorDomain -> {
                ErrorView(
                    stringResource(id = R.string.title_401),
                    stringResource(id = R.string.msg_401),
                    R.drawable.thanos_unauthorized
                )
            }

            FailureDomain.EmptyErrorDomain -> {
                ErrorView(
                    stringResource(R.string.title_empty_error),
                    stringResource(R.string.msg_empty_error),
                    R.drawable.deadpool_no_connection
                )
            }

            FailureDomain.UnknownHostErrorDomain -> {
                ErrorView(
                    stringResource(R.string.title_unknown_host_error),
                    stringResource(R.string.msg_unknown_host_error),
                    R.drawable.captain_empty
                )
            }

            FailureDomain.AnotherErrorDomain -> {
                ErrorView(
                    stringResource(R.string.title_coroutine_error),
                    stringResource(R.string.msg_coroutine_error),
                    R.drawable.groot_error
                )
            }
        }
    }
}