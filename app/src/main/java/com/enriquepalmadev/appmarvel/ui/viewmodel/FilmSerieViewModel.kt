package com.enriquepalmadev.appmarvel.ui.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.domain.usecase.IGetListOfAllSeriesUseCase
import com.enriquepalmadev.appmarvel.domain.usecase.impl.GetListOfAllSeriesUseCaseImpl
import com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.ui.view.extensions.getJsonFromAssets
import com.enriquepalmadev.appmarvel.ui.view.extensions.showSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmSerieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FilmSerieUIState>(FilmSerieUIState.Loading)
    val uiState : StateFlow<FilmSerieUIState> = _uiState
    private val iGetListOfAllSeriesUseCaseImpl = GetListOfAllSeriesUseCaseImpl()

    private var listOfFilmsAndSeries: ArrayList<FilmSerieModel> = ArrayList()

    fun done(){
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceived(listOfFilmsAndSeries))
        }
    }

    suspend fun testingAPIGetAllSeries(){
        val testList = iGetListOfAllSeriesUseCaseImpl.getListOfAllSeries()
        Log.d("testList:::", testList.toString())
    }

    // Obtaining an ArrayList from a JSON file
    fun getListFromJson(context: Context): ArrayList<FilmSerieModel>{
        val json: String = context.getJsonFromAssets("movies.json")
        val filmsAndSeriesList = Gson().fromJson(json, Array<FilmSerieModel>::class.java).toList()
        listOfFilmsAndSeries =  ArrayList(filmsAndSeriesList.sortedByDescending { it.year})
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceived(ArrayList(listOfFilmsAndSeries)))
        } // Pasar estados de error

        return listOfFilmsAndSeries;
    }

    fun initRecyclerView(fsAdapter: FilmSerieAdapter, recyclerView: RecyclerView, arrayList: ArrayList<FilmSerieModel>){
        recyclerView.adapter = fsAdapter // Setting the Adapter in the RecyclerView
        updateItems(fsAdapter, arrayList)
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.RecyclerViewSetted(fsAdapter))
        } // Pasar estados de error
    }

    private fun updateItems(fsAdapter: FilmSerieAdapter, arrayList: ArrayList<FilmSerieModel>){
        fsAdapter.updateList(arrayList)
        /*
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ItemsUpdated)
        } // Pasar estados de error

         */
    }

    // Esta va aquí - BIEN
    fun transferToDataDetail(filmOrSerie: FilmSerieModel){
        val filmsSeriesData = Bundle().apply {
            putSerializable("objectFilmOrSerie", filmOrSerie)
        }
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ItemClicked(filmsSeriesData))
        } // Pasar estados de error
    }

    // Dialog to select the items order
    fun showDialogOrderBy(binding: FragmentFilmsSeriesBinding, context: Context) {

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
                showSnackbar(binding.root, context.getString(R.string.msg_orderby)+" "+selectedItem)

                viewModelScope.launch {
                    _uiState.emit(FilmSerieUIState.OrderingList(listOfFilmsAndSeries, selectedItem, context))
                }
            }
            .setNegativeButton(context.getString(R.string.dialog_cancel)){dialog, which ->
                showSnackbar(binding.root, context.getString(R.string.dialog_canceled))
            }
            .show()
    }

    // Function to order the items of the RecyclerView
    fun orderListBy(arrayList: ArrayList<FilmSerieModel>, selectedItem: String, context: Context) {
        when(selectedItem){
            context.getString(R.string.orderby_year)-> {
                listOfFilmsAndSeries = ArrayList(arrayList.sortedByDescending { it.year })
            }
            context.getString(R.string.orderby_alphabet)-> {
                listOfFilmsAndSeries = ArrayList(arrayList.sortedBy { it.name })
            }
            // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
        }
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceived(listOfFilmsAndSeries))
        }
    }

    fun filteringByName(filmSerieAdapter: FilmSerieAdapter, newText: String){
        val arraylist = listOfFilmsAndSeries.filter { it.name.lowercase().contains(newText) }
        filmSerieAdapter.filterByName(arraylist)
        viewModelScope.launch {
            _uiState.emit(FilmSerieUIState.ListReceived(ArrayList(arraylist)))
        }
    }
}

sealed class FilmSerieUIState {
    data object Loading : FilmSerieUIState()
    data class ListReceived(val arrayList: ArrayList<FilmSerieModel>) : FilmSerieUIState()
    data class Error (val msg: String) : FilmSerieUIState()
    data class RecyclerViewSetted (val adapter: FilmSerieAdapter) : FilmSerieUIState()
    //  data object ItemsUpdated: FilmSerieUIState()
    // data class FilteringList(val arrayList: ArrayList<FilmSerieModel>): FilmSerieUIState()
    data class ItemClicked (val filmSerieData: Bundle) : FilmSerieUIState()
    data class OrderingList (val arrayList: ArrayList<FilmSerieModel>, val itemSelected: String, val context: Context) : FilmSerieUIState()
}
