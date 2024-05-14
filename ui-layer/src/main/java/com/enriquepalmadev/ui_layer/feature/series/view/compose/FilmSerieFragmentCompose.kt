package com.enriquepalmadev.ui_layer.feature.series.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.FilmSerieUIState

@Composable
fun FilmSerieFragmentCompose(uiState: FilmSerieUIState, dialogOrderBy: () -> Unit, itemClicked: (id: Int) -> Unit, favClicked: () -> Unit) {

    when (uiState) {
        is FilmSerieUIState.Error -> {
            when ((uiState).error) {
                is FailureDomain.CustomErrorDomain -> {
                    ErrorView(
                        uiState.error.code,
                        uiState.error.msg,
                        R.drawable.error_404
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

                FailureDomain.CoroutineErrorDomain -> {
                    ErrorView(
                        stringResource(R.string.title_coroutine_error),
                        stringResource(R.string.msg_coroutine_error),
                        R.drawable.groot_error
                    )
                }
            }
        }

        FilmSerieUIState.Loading -> {
            LoadingView()
        }

        is FilmSerieUIState.ListReceived -> {
            Series(
                series = uiState.list,
                dialogOrderBy = dialogOrderBy,
                itemClicked = itemClicked,
                favClicked = favClicked
            )
        }

        else -> Unit
    }

}