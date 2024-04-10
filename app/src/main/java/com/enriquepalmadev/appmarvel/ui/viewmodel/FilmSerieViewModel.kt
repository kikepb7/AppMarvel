package com.enriquepalmadev.appmarvel.ui.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.domain.usecase.impl.GetListOfAllSeriesUseCaseImpl
import com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.ui.view.extensions.showSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmSerieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FilmSerieUIState>(FilmSerieUIState.Loading)
    val uiState : StateFlow<FilmSerieUIState> = _uiState
    private val iGetListOfAllSeriesUseCaseImpl = GetListOfAllSeriesUseCaseImpl()

    private var allSeriesList: ArrayList<FilmSerie> = ArrayList()

    fun done(){
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceivedInLocal(allSeriesList))
        }
    }

    fun getAllSeriesListFromAPI(){
        viewModelScope.launch {
            allSeriesList = iGetListOfAllSeriesUseCaseImpl.getListOfAllSeries()
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListRecievedFromAPI(ArrayList(allSeriesList)))
            }
        }
    }

    fun getAllSeriesListToLocalFromAPI(): ArrayList<FilmSerie>{
        try {
            val filmsAndSeriesList = allSeriesList
            allSeriesList =  ArrayList(filmsAndSeriesList.sortedByDescending { it.id})
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListReceivedInLocal(ArrayList(allSeriesList)))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
        return allSeriesList;
    }

    fun initRecyclerView(fsAdapter: FilmSerieAdapter, recyclerView: RecyclerView, arrayList: ArrayList<FilmSerie>){
        try {
            recyclerView.adapter = fsAdapter // Setting the Adapter in the RecyclerView
            fsAdapter.updateList(arrayList) // Updated the list in the Adapter and, in consequence, in the RecyclerView
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.RecyclerViewSetted(fsAdapter))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }

    // Esta va aquí - BIEN
    fun transferToDataDetail(filmOrSerie: FilmSerie){
        try {
            val filmsSeriesData = Bundle().apply {
                putSerializable("objectFilmOrSerie", filmOrSerie)
            }
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ItemClicked(filmsSeriesData))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }

    // Dialog to select the items order
    fun showDialogOrderBy(context: Context) {
        try {
            var selectedItemIndex :Int = 0
            val arrayItemsOrderBy = arrayOf(
                context.getString(R.string.orderby_year),
                context.getString(R.string.orderby_alphabet),
                context.getString(R.string.orderby_fav_first),
                context.getString(R.string.orderby_fav_only)
            )
            var selectedItem = arrayItemsOrderBy[selectedItemIndex]

            MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.dialog_title))
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) {dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(context.getString(R.string.dialog_ok)){dialog, which ->
                    viewModelScope.launch {
                        _uiState.emit(FilmSerieUIState.OrderingList(allSeriesList, selectedItem, context))
                    }
                }
                .setNegativeButton(context.getString(R.string.dialog_cancel)){dialog, which ->
                }
                .show()
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }

    // Function to order the items of the RecyclerView
    fun orderListBy(arrayList: ArrayList<FilmSerie>, selectedItem: String, context: Context, binding: FragmentFilmsSeriesBinding) {
        try {
            when(selectedItem){
                context.getString(R.string.orderby_year)-> {
                    allSeriesList = ArrayList(arrayList.sortedByDescending { it.id })
                    showSnackbar(binding.root, context.getString(R.string.msg_orderby)+" "+selectedItem)
                }
                context.getString(R.string.orderby_alphabet)-> {
                    allSeriesList = ArrayList(arrayList.sortedBy { it.title })
                    showSnackbar(binding.root, context.getString(R.string.msg_orderby)+" "+selectedItem)
                }
                // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
                // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
            }
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListReceivedInLocal(allSeriesList))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }

    fun filteringByName(filmSerieAdapter: FilmSerieAdapter, newText: String){
        try {
            val arraylist = allSeriesList.filter { it.title.lowercase().contains(newText) }
            filmSerieAdapter.filterByName(arraylist)
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListReceivedInLocal(ArrayList(arraylist)))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }
}

sealed class FilmSerieUIState {
    data class Error (val msg: String) : FilmSerieUIState()
    data object Loading : FilmSerieUIState()
    data class ListRecievedFromAPI(val arrayList: ArrayList<FilmSerie>) : FilmSerieUIState()
    data class ListReceivedInLocal(val arrayList: ArrayList<FilmSerie>) : FilmSerieUIState()
    data class RecyclerViewSetted (val adapter: FilmSerieAdapter) : FilmSerieUIState()
    data class ItemClicked (val filmSerieData: Bundle) : FilmSerieUIState()
    data class OrderingList (val arrayList: ArrayList<FilmSerie>, val itemSelected: String, val context: Context) : FilmSerieUIState()
}
