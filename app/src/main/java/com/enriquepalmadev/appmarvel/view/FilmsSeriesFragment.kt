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
import com.google.gson.Gson

class FilmsSeriesFragment : Fragment() {
    private lateinit var filmsSeriesAdapter :FilmsSeriesAdapter
    private lateinit var bindingFilmsSeries: FragmentFilmsSeriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFilmsSeries = FragmentFilmsSeriesBinding.inflate(layoutInflater)

        Log.d(":::JSON", getListFromJson().toString())
        filmsSeriesAdapter = FilmsSeriesAdapter()
        bindingFilmsSeries.rvFilmsSeries.adapter = filmsSeriesAdapter

        filmsSeriesAdapter.refreshList(getListFromJson())


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films_series, container, false)
    }

    private fun getListFromJson(): ArrayList<FilmsSeriesDataclass>{
        // Call to the function if context is not null
        val json: String? = context?.let { getJsonFromAssets(it,"movies.json") }
        val filmsAndSeriesList = Gson().fromJson(json, Array<FilmsSeriesDataclass>::class.java).toList()

        return ArrayList(filmsAndSeriesList)
    }
}