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
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieUIState
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieViewModel
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
    }

    private fun initObserver(){
        fsViewModel.uiState.onEach { uiState ->
            when(uiState){
                is FilmSerieUIState.Error -> {
                    Toast.makeText(context, "Error: ${uiState.msg}", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                FilmSerieUIState.Loading -> {
                    fsViewModel.getAllSeriesListFromAPI()
                    // ProgressBar Show
                }
                is FilmSerieUIState.ListRecievedFromAPI -> {
                    context?.let { fsViewModel.getAllSeriesListToLocalFromAPI() }
                    // ProgressBar Gone
                }

                is FilmSerieUIState.ListReceivedInLocal -> {
                    // With uiState, I can access to the list that returns (and emit) the state
                    fsViewModel.initRecyclerView(fsAdapter, fsBinding.rvFilmsSeries, uiState.arrayList)
                    // ProgressBar Gone
                }
                is FilmSerieUIState.RecyclerViewSetted -> {
                    Toast.makeText(context, "RecyclerViewSetted...", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                is FilmSerieUIState.ItemClicked -> {
                    findNavController().navigate(R.id.action_filmsAndSeriesFragment_to_itemDetailsFilmsSeriesFragment, uiState.filmSerieData)
                    fsViewModel.done()
                    // ProgressBar Gone
                }
                is FilmSerieUIState.OrderingList -> {
                    fsViewModel.orderListBy(uiState.arrayList, uiState.itemSelected, uiState.context, fsBinding)
                    // ProgressBar Gone
                }
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun sendingListenerToAdapterItems() {
        fsAdapter = FilmSerieAdapter(::onFilmSerieClicked) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
    }

    private fun onFilmSerieClicked(filmOrSerie: FilmSerie){
        fsViewModel.transferToDataDetail(filmOrSerie)
    }

    private fun btnOrderByOnClick(){
        fsBinding.btnOrderby.setOnClickListener { context?.let { fsViewModel.showDialogOrderBy(it) } }
    }

    private fun svOnQueryTextChange(){
        fsBinding.searchViewSeriesAndFilms.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                fsViewModel.filteringByName(fsAdapter, newText)
                return false
            }
        })
    }
}