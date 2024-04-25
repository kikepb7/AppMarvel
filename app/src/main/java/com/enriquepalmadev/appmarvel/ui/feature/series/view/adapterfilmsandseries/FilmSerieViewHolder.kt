package com.enriquepalmadev.appmarvel.ui.feature.series.view.adapterfilmsandseries

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.feature.series.view.extensions.loadImage


class FilmSerieViewHolder(private val binding: ItemFilmsSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    // Binding to access to view objects on item_films_series.xml

    fun bind(
        filmSerieModel: FilmSerieModel,
        itemListener: (Int) -> Unit,
        favListener: (Int, String, ImageView) -> Unit
    ){
        val completeImagePath = "${filmSerieModel.thumbnailPath}.${filmSerieModel.thumbnailExt}"
        binding.apply {
            titleFilmsSeries.text = filmSerieModel.title
            imageFilmsSeries.apply {
                loadImage(completeImagePath)
                setOnClickListener { itemListener.invoke(filmSerieModel.id) }
            }
            btnFav.setOnClickListener{favListener.invoke(filmSerieModel.id,
                it.contentDescription.toString(),
                btnFav
            )}
        }
    }
}