package com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.view.extensions.loadImage


class FilmSerieViewHolder(private val binding: ItemFilmsSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    // Binding to access to view objects on item_films_series.xml

    val favFilmsSeries :ArrayList<Int> = ArrayList()


    fun bind(filmSerie: FilmSerie){
        val completeImagePath = "${filmSerie.thumbnailPath}.${filmSerie.thumbnailExt}"

        binding.titleFilmsSeries.text = filmSerie.title
        binding.imageFilmsSeries.loadImage(completeImagePath)

        binding.btnFav.setOnClickListener(View.OnClickListener {
            if(binding.btnFav.contentDescription=="on"){
                binding.btnFav.setImageResource(R.drawable.ic_border_favorite_24dp)
                binding.btnFav.contentDescription = "off"
                favFilmsSeries.remove(filmSerie.id)
            } else if(binding.btnFav.contentDescription=="off") {
                binding.btnFav.setImageResource(R.drawable.ic_full_favorite_24dp)
                binding.btnFav.contentDescription = "on"
                favFilmsSeries.add(filmSerie.id)
            }
        })
    }
}