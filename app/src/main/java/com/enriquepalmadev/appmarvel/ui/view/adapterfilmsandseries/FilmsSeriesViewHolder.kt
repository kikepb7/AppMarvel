package com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage


class FilmsSeriesViewHolder(private val binding: ItemFilmsSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    // Binding to access to view objects on item_films_series.xml
    val favFilmsSeries :ArrayList<Int> = ArrayList<Int>()

    fun bind(film_serie: FilmsSeriesDataclass){
        binding.titleFilmsSeries.text = film_serie.name
        binding.imageFilmsSeries.loadImage(film_serie.cover)

        binding.btnFav.setOnClickListener(View.OnClickListener {
            if(binding.btnFav.contentDescription=="on"){
                binding.btnFav.setImageResource(R.drawable.ic_border_favorite_24dp)
                binding.btnFav.contentDescription = "off"
                favFilmsSeries.remove(film_serie.id)
            } else if(binding.btnFav.contentDescription=="off") {
                binding.btnFav.setImageResource(R.drawable.ic_full_favorite_24dp)
                binding.btnFav.contentDescription = "on"
                favFilmsSeries.add(film_serie.id)
            }
        })
    }
}