package com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass

class FilmsSeriesAdapter(private val listener: (FilmsSeriesDataclass) -> Unit): RecyclerView.Adapter<FilmsSeriesViewHolder>() {
    private val filmsSeriesList = arrayListOf<FilmsSeriesDataclass>()

    // Maybe it will cause an error
    // This method returns the fragment's view inflate with the items' view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsSeriesViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmsSeriesViewHolder(binding)
    }

    // Number of items
    override fun getItemCount(): Int = filmsSeriesList.size

    // This method paint for each item (position) the ViewHolder (a "wrapper" of a view)
    override fun onBindViewHolder(holder: FilmsSeriesViewHolder, position: Int) {
        val film_serie :FilmsSeriesDataclass = filmsSeriesList[position]
        holder.bind(film_serie)
        // Adding the listener to each item
        // With this implementation we can extract the listener method to another class
        holder.itemView.setOnClickListener{listener(film_serie)}
    }

    // This method filled the list in the RecyclerView
    fun refreshList(filmsSeriesList: ArrayList<FilmsSeriesDataclass>): ArrayList<FilmsSeriesDataclass>{
        this.filmsSeriesList.clear()
        this.filmsSeriesList.addAll(filmsSeriesList)
        notifyDataSetChanged() // This method refresh the list in the screen because we are notifying the changes
        return filmsSeriesList
    }

    fun filterByName(filmsSeries: List<FilmsSeriesDataclass>){
        this.filmsSeriesList.clear()
        this.filmsSeriesList.addAll(filmsSeries)
        notifyDataSetChanged()
    }
}