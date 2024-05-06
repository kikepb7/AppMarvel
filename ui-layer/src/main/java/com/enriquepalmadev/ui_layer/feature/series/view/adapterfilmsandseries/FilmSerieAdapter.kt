package com.enriquepalmadev.ui_layer.feature.series.view.adapterfilmsandseries

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import com.enriquepalmadev.ui_layer.databinding.ItemFilmsSeriesBinding

class FilmSerieAdapter(
    private val itemListener: (Int) -> Unit,
    private val favListener: (Int, String, ImageView) -> Unit
) : RecyclerView.Adapter<FilmSerieViewHolder>() {

    private val fsList = arrayListOf<FilmSerieModel>()

    // This method returns the fragment's view inflate with the items' view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmSerieViewHolder {
        val binding =
            ItemFilmsSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmSerieViewHolder(binding)
    }

    override fun getItemCount(): Int = fsList.size // Number of items

    override fun onBindViewHolder(holder: FilmSerieViewHolder, position: Int) {
        // This method paint for each item (position) the ViewHolder (a "wrapper" of a view)
        val filmSerieModel: FilmSerieModel = fsList[position]
        // Adding the listener to each item
        // With this implementation we can extract the listener method to another class
        holder.bind(filmSerieModel, itemListener, favListener)
    }

    fun updateList(filmsSeriesList: List<FilmSerieModel>) {
        // This method updated the list in the Adapter
        // This method is used too to filter with the SearchView
        this.fsList.clear()
        this.fsList.addAll(filmsSeriesList)
    }

}