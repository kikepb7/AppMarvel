package com.enriquepalmadev.appmarvel.ui.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.impl.GetListOfAllSeriesUseCaseImpl
import com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.ui.view.extensions.showSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FilmSerieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FilmSerieUIState>(FilmSerieUIState.Loading)
    val uiState : StateFlow<FilmSerieUIState> = _uiState
    private val iGetListOfAllSeriesUseCaseImpl = GetListOfAllSeriesUseCaseImpl()

    private var allSeriesList: ArrayList<FilmSerieModel> = ArrayList()

    fun done(){
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceivedInViewModel(allSeriesList))
        }
    }

    fun getAllSeriesListFromAPI(){
        viewModelScope.launch {
            iGetListOfAllSeriesUseCaseImpl.getListOfAllSeries()
                .onStart { _uiState.emit(FilmSerieUIState.Loading)}
                .catch { _uiState.emit(FilmSerieUIState.Error(it.toString())) }
                .collect {
                    allSeriesList = it
                    _uiState.emit(FilmSerieUIState.ListReceivedFromAPI(it))
                }
        }
    }

    fun getAllSeriesListToLocalFromAPI(): ArrayList<FilmSerieModel>{
        try {
            val filmsAndSeriesList = allSeriesList
            allSeriesList =  ArrayList(filmsAndSeriesList.sortedByDescending { it.startYear})
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListReceivedInViewModel(ArrayList(allSeriesList)))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
        return allSeriesList;
    }

    fun transferToDataDetail(filmOrSerie: FilmSerieModel){
        try {
            val filmsSeriesData = Bundle().apply {
                putInt("idSerie", filmOrSerie.id)
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

    // Function to order the items of the RecyclerView
    fun orderListBy(selectedItem: String, context: Context, binding: FragmentFilmsSeriesBinding) {
        try {
            when(selectedItem){
                context.getString(R.string.orderby_year)-> {
                    allSeriesList = ArrayList(allSeriesList.sortedByDescending { it.startYear })
                    showSnackbar(binding.root, context.getString(R.string.msg_orderby)+" "+selectedItem)
                }
                context.getString(R.string.orderby_alphabet)-> {
                    allSeriesList = ArrayList(allSeriesList.sortedBy { it.title })
                    showSnackbar(binding.root, context.getString(R.string.msg_orderby)+" "+selectedItem)
                }
                // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
                // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
            }
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.ListReceivedInViewModel(allSeriesList))
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
                _uiState.emit(FilmSerieUIState.ListReceivedInViewModel(ArrayList(arraylist)))
            }
        } catch (e: Exception){
            viewModelScope.launch {
                _uiState.emit(FilmSerieUIState.Error(e.message.toString()))
            }
        }
    }
}

sealed class FilmSerieUIState {
    data class Error(val msg: String) : FilmSerieUIState()
    data object Loading : FilmSerieUIState()
    data class ListReceivedFromAPI(val arrayList: ArrayList<FilmSerieModel>) : FilmSerieUIState()
    data class ListReceivedInViewModel(val arrayList: ArrayList<FilmSerieModel>) : FilmSerieUIState()
    data class ItemClicked(val filmSerieData: Bundle) : FilmSerieUIState()
}
