package com.enriquepalmadev.ui_layer.feature.series.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.series.compose.ErrorView
import com.enriquepalmadev.ui_layer.feature.series.compose.FilmSerieFragmentCompose
import com.enriquepalmadev.ui_layer.feature.series.compose.LoadingView
import com.enriquepalmadev.ui_layer.feature.series.compose.Series
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.FilmSerieUIState
import com.enriquepalmadev.ui_layer.feature.series.viewmodel.FilmSerieViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@AndroidEntryPoint
class FilmSerieFragment : Fragment() {

    private val viewModel: FilmSerieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                // View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    FilmSerieFragmentCompose(
                        uiState = uiState,
                        dialogOrderBy = ::showDialogOrderBy,
                        itemClicked = { id ->
                            onFilmSerieClicked(
                                findNavController(), id
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sendingListenerToAdapterItems()
        //svOnQueryTextChange()

        viewModel.getAllSeriesListFromAPI()
    }

    // Dialog to select the items order
    private fun showDialogOrderBy() {
        var selectedItemIndex: Int = 0
        val arrayItemsOrderBy = arrayOf(
            getString(R.string.orderby_year),
            getString(R.string.orderby_alphabet),
            getString(R.string.orderby_fav_first),
            getString(R.string.orderby_fav_only)
        )
        var selectedItem = arrayItemsOrderBy[selectedItemIndex]
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.dialog_title))
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) { _, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                    orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)) { _, _ ->
                }
                .show()
        }
    }

    // Function to order the items of the RecyclerView
    private fun orderListBy(selectedItem: String, context: Context) {
        when (selectedItem) {
            context.getString(R.string.orderby_year) -> viewModel.orderListByStartYear()
            context.getString(R.string.orderby_alphabet) -> viewModel.orderListByAlphabet()
            // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
        }
    }

    private fun onFilmSerieClicked(navController: NavController, id: Int) {
        navController.navigate(
            FilmSerieFragmentDirections.actionFilmSerieFragmentToItemDetailsFilmsSeriesFragment(id)
        )
    }


    private fun onSearchQueryChange(newText: String) {
        viewModel.filteringByName(newText)
    }

    /*
    private fun onFavIconClicked(id: Int, favState: String, favBtn: ImageView) {
        // fsViewModel.favSerie(id, favState) TODO() Function to the database persist
        favBtn.apply {
            if (contentDescription == "on") {
                setImageResource(R.drawable.ic_border_favorite_24dp)
                contentDescription = "off"
            } else if (contentDescription == "off") {
                setImageResource(R.drawable.ic_full_favorite_24dp)
                contentDescription = "on"
            }
        }
    }
     */

}
