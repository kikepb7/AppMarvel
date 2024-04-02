package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.adapterfilmsandseries.FilmsSeriesAdapter
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.view.utilsfilmsseries.getJsonFromAssets
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class FilmsSeriesFragment : Fragment() {
    private lateinit var filmsSeriesAdapter :FilmsSeriesAdapter
    private lateinit var bindingFilmsSeries: FragmentFilmsSeriesBinding
    private var listOfFilmsAndSeries: ArrayList<FilmsSeriesDataclass> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFilmsSeries = FragmentFilmsSeriesBinding.inflate(inflater)

        Log.d(":::JSON", getListFromJson().toString())
        filmsSeriesAdapter = FilmsSeriesAdapter()
        bindingFilmsSeries.rvFilmsSeries.adapter = filmsSeriesAdapter

        // Getting the list of Films and Series
        listOfFilmsAndSeries = filmsSeriesAdapter.refreshList(getListFromJson())

        // When we press the button "Order by..."
        bindingFilmsSeries.btnOrderby.setOnClickListener(View.OnClickListener {
            listOfFilmsAndSeries = dialogOrderBy(bindingFilmsSeries)
        })


        // Inflate the layout for this fragment
        return bindingFilmsSeries.root
    }

    // Obtain an ArrayList from a JSON file
    private fun getListFromJson(): ArrayList<FilmsSeriesDataclass>{
        // Call to the function if context is not null
        val json: String? = context?.let { getJsonFromAssets(it,"movies.json") }
        val filmsAndSeriesList = Gson().fromJson(json, Array<FilmsSeriesDataclass>::class.java).toList()

        return ArrayList(filmsAndSeriesList.sortedByDescending { it.year})
    }

    // Function to order the items of the RecyclerView
    private fun orderListBy(arrayList: ArrayList<FilmsSeriesDataclass>, selectedItem: String): ArrayList<FilmsSeriesDataclass> {
        when(selectedItem){
            getString(R.string.orderby_year)-> arrayList.sortedByDescending { it.year }
            getString(R.string.orderby_alphabet)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
        }
        return arrayList
    }

    // Dialog to select the items order
    private fun dialogOrderBy(binding: FragmentFilmsSeriesBinding): ArrayList<FilmsSeriesDataclass> {

        var selectedItemIndex :Int = 0
        val arrayItems = arrayOf(getString(R.string.orderby_year),
            getString(R.string.orderby_alphabet),
            getString(R.string.orderby_fav_first),
            getString(R.string.orderby_fav_only))
        var selectedItem = arrayItems[selectedItemIndex]

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.dialog_title))
                .setSingleChoiceItems(arrayItems, selectedItemIndex) {dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItems[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)){dialog, which ->
                    showSnackbar(binding.root, getString(R.string.msg_orderby)+" "+selectedItem)
                    listOfFilmsAndSeries = orderListBy(listOfFilmsAndSeries, selectedItem)
                    filmsSeriesAdapter.refreshList(listOfFilmsAndSeries)
                }
                .setNegativeButton(getString(R.string.dialog_cancel)){dialog, which ->
                    showSnackbar(binding.root, getString(R.string.dialog_canceled))
                }
                .show()
        }
        return listOfFilmsAndSeries
    }

    // Snackbar function
    private fun showSnackbar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}