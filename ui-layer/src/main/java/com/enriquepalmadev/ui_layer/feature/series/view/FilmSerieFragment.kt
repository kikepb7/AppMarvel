package com.enriquepalmadev.ui_layer.feature.series.compose

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.ui_layer.R
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
                // View
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val uiState by viewModel.uiState.collectAsState()

                    when (uiState) {
                        is FilmSerieUIState.Error -> {
                            // TODO() showLoading(false)
                            when ((uiState as FilmSerieUIState.Error).error) {
                                is FailureDomain.CustomErrorDomain -> {
                                    // TODO() setErrorView(uiState.error.code, uiState.error.msg)
                                    // TODO() showErrorView(true, R.drawable.groot_error)
                                }

                                FailureDomain.UnauthorizedErrorDomain -> {
                                    // TODO() setErrorView(getString(R.string.title_401), getString(R.string.msg_401))
                                    // TODO() showErrorView(true, R.drawable.thanos_unauthorized)
                                }

                                FailureDomain.EmptyErrorDomain -> {
                                    /* // TODO()
                                setErrorView(
                                    getString(R.string.title_empty_error),
                                    getString(R.string.msg_empty_error)
                                )
                                 */
                                    // TODO() showErrorView(true, R.drawable.deadpool_no_connection)
                                }

                                FailureDomain.UnknownHostErrorDomain -> {
                                    /* // TODO()
                                setErrorView(
                                    getString(R.string.title_unknown_host_error),
                                    getString(R.string.msg_unknown_host_error)
                                )
                                 */
                                    // TODO() showErrorView(true, R.drawable.captain_empty)
                                }

                                FailureDomain.CoroutineErrorDomain -> {
                                    /* // TODO()
                                setErrorView(
                                    getString(R.string.title_coroutine_error),
                                    getString(R.string.msg_coroutine_error)
                                )
                                 */
                                    // TODO() showErrorView(true, R.drawable.groot_error)
                                }
                            }
                        }

                        FilmSerieUIState.Loading -> {
                            // TODO() showErrorView(false, null)
                            // TODO() showLoading(true)
                        }

                        is FilmSerieUIState.ListReceived -> {
                            Series(
                                series = (uiState as FilmSerieUIState.ListReceived).list,
                                dialogOrderBy = ::showDialogOrderBy
                            )
                            // TODO() showLoading(false)
                            // TODO() showErrorView(false, null)
                        }

                        is FilmSerieUIState.ItemClicked -> {
                            val action =
                                FilmSerieFragmentDirections.actionFilmSerieFragmentToItemDetailsFilmsSeriesFragment(
                                    (uiState as FilmSerieUIState.ItemClicked).idSerie
                                )
                            findNavController().navigate(action)
                            // TODO() fsViewModel.done()
                            // TODO() showLoading(false)
                            // TODO() showErrorView(false, null)
                        }
                    }
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
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) { dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)) { dialog, which ->
                    orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, which ->
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

    private fun onFilmSerieClicked(id: Int) {
        viewModel.transferToDataDetail(id)
    }

    /*
    private fun sendingListenerToAdapterItems() {
        fsAdapter = FilmSerieAdapter(
            ::onFilmSerieClicked,
            ::onFavIconClicked
        ) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
    }

    private fun initRecyclerView(list: List<FilmSerieModel>) {
        fsBinding.rvFilmsSeries.adapter = fsAdapter // Setting the Adapter in the RecyclerView
        fsAdapter.updateList(list) // Updated the list in the Adapter and, in consequence, in the RecyclerView
    }



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

    private fun btnOrderByOnClick() {
        fsBinding.btnOrderby.setOnClickListener { showDialogOrderBy() }
    }

    private fun svOnQueryTextChange() {
        fsBinding.searchViewSeriesAndFilms.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                fsViewModel.filteringByName(newText)
                return false
            }
        })
    }

     */

}
