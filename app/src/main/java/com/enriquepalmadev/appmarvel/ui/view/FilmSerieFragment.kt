package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieUIState
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmSerieFragment : Fragment() {

    private val fsViewModel: FilmSerieViewModel by viewModels()
    private lateinit var fsAdapter : FilmSerieAdapter
    private lateinit var fsBinding: FragmentFilmsSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fsBinding = FragmentFilmsSeriesBinding.inflate(inflater, container, false)
        return fsBinding.root // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendingListenerToAdapterItems()
        btnOrderByOnClick()
        svOnQueryTextChange()
        initObserver()

        fsViewModel.getAllSeriesListFromAPI()
    }

    private fun initObserver(){
        fsViewModel.uiState.onEach { uiState ->
            when(uiState){
                is FilmSerieUIState.Error -> {
                    // With uiState, I can access to the list that returns (and emits) the state
                    Toast.makeText(context, "Error: ${uiState.msg}", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                FilmSerieUIState.Loading -> {
                    // fsViewModel.getAllSeriesListFromAPI()
                    // ProgressBar Show
                }
                is FilmSerieUIState.ListReceived -> {
                    initRecyclerView(uiState.arrayList)
                    // ProgressBar Gone
                }

                is FilmSerieUIState.ItemClicked -> {
                    val action = FilmSerieFragmentDirections.actionFilmsAndSeriesFragmentToItemDetailsFilmsSeriesFragment(uiState.idSerie)
                    findNavController().navigate(action)
                    fsViewModel.done()
                    // ProgressBar Gone
                }
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun sendingListenerToAdapterItems() {
        fsAdapter = FilmSerieAdapter(::onFilmSerieClicked) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
    }

    private fun initRecyclerView(arrayList: ArrayList<FilmSerieModel>){
        fsBinding.rvFilmsSeries.adapter = fsAdapter // Setting the Adapter in the RecyclerView
        fsAdapter.updateList(arrayList) // Updated the list in the Adapter and, in consequence, in the RecyclerView
    }

    private fun onFilmSerieClicked(filmOrSerie: FilmSerieModel){
        fsViewModel.transferToDataDetail(filmOrSerie)
    }

    private fun btnOrderByOnClick(){
        fsBinding.btnOrderby.setOnClickListener { showDialogOrderBy() }
    }

    // Dialog to select the items order
    private fun showDialogOrderBy() {

        var selectedItemIndex :Int = 0
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
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) {dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)){dialog, which ->
                    fsViewModel.orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)){dialog, which ->
                }
                .show()
        }
    }

    private fun svOnQueryTextChange(){
        fsBinding.searchViewSeriesAndFilms.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                fsViewModel.filteringByName(newText)
                return false
            }
        })
    }
}