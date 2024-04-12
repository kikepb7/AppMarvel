package com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel

class FilmSerieAdapter(private val listener: (FilmSerieModel) -> Unit): RecyclerView.Adapter<FilmSerieViewHolder>() {

    private val fsList = arrayListOf<FilmSerieModel>()

    // This method returns the fragment's view inflate with the items' view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmSerieViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmSerieViewHolder(binding)
    }

    override fun getItemCount(): Int = fsList.size // Number of items

    override fun onBindViewHolder(holder: FilmSerieViewHolder, position: Int) {
        // This method paint for each item (position) the ViewHolder (a "wrapper" of a view)
        val filmSerieModel : FilmSerieModel = fsList[position]
        holder.bind(filmSerieModel)
        // Adding the listener to each item
        // With this implementation we can extract the listener method to another class
        holder.itemView.setOnClickListener{listener(filmSerieModel)}
    }

    fun updateList(filmsSeriesList: ArrayList<FilmSerieModel>) {
        // This method updated the list in the Adapter
        // This method is used too to filter with the SearchView
        this.fsList.clear()
        this.fsList.addAll(filmsSeriesList)
    }

}