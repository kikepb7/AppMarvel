package com.enriquepalmadev.appmarvel.adapterfilmsandseries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass

class FilmsSeriesAdapter: RecyclerView.Adapter<FilmsSeriesViewHolder>() {
    private val filmsSeriesList = arrayListOf<FilmsSeriesDataclass>()

    // Maybe it will cause an error
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsSeriesViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmsSeriesViewHolder(binding)
    }

    // Number of items
    override fun getItemCount(): Int = filmsSeriesList.size

    override fun onBindViewHolder(holder: FilmsSeriesViewHolder, position: Int) {
        val film_serie :FilmsSeriesDataclass = filmsSeriesList[position]
        holder.bind(film_serie)
    }

    fun refreshList(filmsSeriesList: ArrayList<FilmsSeriesDataclass>): ArrayList<FilmsSeriesDataclass>{
        this.filmsSeriesList.clear()
        this.filmsSeriesList.addAll(filmsSeriesList)
        notifyDataSetChanged()
        return filmsSeriesList
    }
}