package com.enriquepalmadev.ui_layer.feature.comics.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.compose.ComicListScreen
import com.enriquepalmadev.ui_layer.feature.comics.viewmodel.ComicsViewModel
import com.enriquepalmadev.ui_layer.feature.comics.viewmodel.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ComicsFragment : Fragment() {

    private val viewModel: ComicsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            val viewModel: ComicsViewModel by viewModels()

            initObserver()

            setContent {
                val state by viewModel.state.collectAsState()

                state?.let { stateModel ->

                    ComicListScreen(
                        state = stateModel,
                        onComicClicked = {
                            viewModel.navigateToComicDetail(it.id)
                        },
                        onBackButtonClicked = {
                            viewModel.navigateToHome()
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        We call viewModel.getComicList() only if savedInstanceState is null
        We ensures the comics list is only loaded the first time the fragment is created
        */
        if (savedInstanceState == null) {
            viewModel.getComicsList()
        }
    }

    private fun initObserver() {
        viewModel.event.onEach { state ->
            when (state) {
                is Event.NavigateToDetail -> {
                    findNavController().navigate(
                        ComicsFragmentDirections.actionComicsFragmentToComicDetail(
                            id = state.comicId
                        )
                    )
                    viewModel.idle()
                }

                Event.NavigateToHome -> {
                    findNavController().navigate(R.id.homeFragment)
                }
                Event.Idle -> {}
                null -> {}

            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}