package com.enriquepalmadev.appmarvel.adapterfilmsandseries

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.view.utilsfilmsseries.loadImage


class FilmsSeriesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    // Binding to access to view objects on item_films_series.xml
    private lateinit var binding: ItemFilmsSeriesBinding


    fun bind(film_serie: FilmsSeriesDataclass){
        binding.textFilmsSeries.text = film_serie.name
        binding.imageFilmsSeries.loadImage(film_serie.cover)
    }
}