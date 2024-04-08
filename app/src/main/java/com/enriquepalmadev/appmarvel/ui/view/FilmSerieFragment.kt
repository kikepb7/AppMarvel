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
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieUIState
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmSerieFragment : Fragment(), View.OnClickListener, SearchView.OnQueryTextListener {

    private val fsViewModel: FilmSerieViewModel by viewModels()
    private lateinit var fsAdapter : FilmSerieAdapter
    private lateinit var fsBinding: FragmentFilmsSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fsBinding = FragmentFilmsSeriesBinding.inflate(inflater)
        return fsBinding.root // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fsAdapter = FilmSerieAdapter(::onFilmSerieClicked) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
        settingListeners()
        initObserver()
    }

    private fun initObserver(){
        fsViewModel.uiState.onEach { uiState ->
            when(uiState){
                is FilmSerieUIState.Error -> {
                    Toast.makeText(context, "Error...", Toast.LENGTH_LONG).show()
                    // ProgressBar Gone
                }
                FilmSerieUIState.Loading -> {
                    context?.let { fsViewModel.getListFromJson(it) }
                    // ProgressBar Show
                }
                is FilmSerieUIState.ListReceived -> {
                    fsViewModel.initRecyclerView(fsAdapter, fsBinding.rvFilmsSeries, uiState.arrayList) // With uiState, I can access to the list that returns (and emit) the state
                    // ProgressBar Gone
                }
                is FilmSerieUIState.RecyclerViewSetted -> {
                    //fsViewModel.updateItems(uiState.adapter,)
                    Toast.makeText(context, "RecyclerViewSetted...", Toast.LENGTH_LONG).show()
                }
                is FilmSerieUIState.ItemClicked -> {
                    findNavController().navigate(R.id.action_filmsAndSeriesFragment_to_itemDetailsFilmsSeriesFragment, uiState.filmSerieData)
                    fsViewModel.done()
                }
                is FilmSerieUIState.OrderingList -> fsViewModel.orderListBy(uiState.arrayList, uiState.itemSelected, uiState.context)
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // Esta no se si va aquí
    private fun onFilmSerieClicked(filmOrSerie: FilmSerieModel){
        fsViewModel.transferToDataDetail(filmOrSerie)
    }

    private fun settingListeners(){
        fsBinding.btnOrderby.setOnClickListener(this)
        fsBinding.searchViewSeriesAndFilms.setOnQueryTextListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            fsBinding.btnOrderby -> {
                context?.let { fsViewModel.showDialogOrderBy(fsBinding, it) }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            fsViewModel.filteringByName(fsAdapter, newText)
        }
        return false
    }
}