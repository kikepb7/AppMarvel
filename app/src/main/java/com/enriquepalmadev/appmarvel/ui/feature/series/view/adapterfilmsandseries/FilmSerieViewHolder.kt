package com.enriquepalmadev.appmarvel.ui.feature.series.view.adapterfilmsandseries

import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.feature.series.view.extensions.loadImage


class FilmSerieViewHolder(private val binding: ItemFilmsSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    // Binding to access to view objects on item_films_series.xml

    val favFilmsSeries :ArrayList<Int> = ArrayList()


    fun bind(filmSerieModel: FilmSerieModel){
        val completeImagePath = "${filmSerieModel.thumbnailPath}.${filmSerieModel.thumbnailExt}"

        binding.titleFilmsSeries.text = filmSerieModel.title
        binding.imageFilmsSeries.loadImage(completeImagePath)

        binding.btnFav.setOnClickListener {
            if(binding.btnFav.contentDescription=="on"){
                binding.btnFav.setImageResource(R.drawable.ic_border_favorite_24dp)
                binding.btnFav.contentDescription = "off"
                favFilmsSeries.remove(filmSerieModel.id)
            } else if(binding.btnFav.contentDescription=="off") {
                binding.btnFav.setImageResource(R.drawable.ic_full_favorite_24dp)
                binding.btnFav.contentDescription = "on"
                favFilmsSeries.add(filmSerieModel.id)
            }
        }
    }
}