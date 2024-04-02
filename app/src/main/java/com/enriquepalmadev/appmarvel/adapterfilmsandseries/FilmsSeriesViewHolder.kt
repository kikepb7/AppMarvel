package com.enriquepalmadev.appmarvel.adapterfilmsandseries

import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.view.utilsfilmsseries.loadImage


class FilmsSeriesViewHolder(private val binding: ItemFilmsSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    // Binding to access to view objects on item_films_series.xml

    fun bind(film_serie: FilmsSeriesDataclass){
        binding.textFilmsSeries.text = film_serie.name
        binding.imageFilmsSeries.loadImage(film_serie.cover)
    }
}