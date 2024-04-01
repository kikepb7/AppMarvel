package com.enriquepalmadev.appmarvel.adapterfilmsandseries

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.view.utilsfilmsseries.inflate

class FilmsSeriesAdapter: RecyclerView.Adapter<FilmsSeriesViewHolder>() {
    private val filmsSeriesList = arrayListOf<FilmsSeriesDataclass>()

    // Maybe it will cause an error
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsSeriesViewHolder {
        val view :View = parent.inflate(R.layout.item_films_series)
        return FilmsSeriesViewHolder(view)
    }

    // Number of items
    override fun getItemCount(): Int = filmsSeriesList.size

    override fun onBindViewHolder(holder: FilmsSeriesViewHolder, position: Int) {
        val film_serie :FilmsSeriesDataclass = filmsSeriesList[position]
        holder.bind(film_serie)
    }

    fun refreshList(filmsSeriesList: ArrayList<FilmsSeriesDataclass>){
        filmsSeriesList.addAll(this.filmsSeriesList)
        notifyDataSetChanged()
    }
}