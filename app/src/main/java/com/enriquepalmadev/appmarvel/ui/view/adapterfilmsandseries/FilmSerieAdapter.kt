package com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel

class FilmSerieAdapter(private val listener: (FilmSerie) -> Unit): RecyclerView.Adapter<FilmSerieViewHolder>() {

    private val fsList = arrayListOf<FilmSerie>()

    // This method returns the fragment's view inflate with the items' view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmSerieViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmSerieViewHolder(binding)
    }

    override fun getItemCount(): Int = fsList.size // Number of items

    override fun onBindViewHolder(holder: FilmSerieViewHolder, position: Int) {
        // This method paint for each item (position) the ViewHolder (a "wrapper" of a view)
        val filmSerie : FilmSerie = fsList[position]
        holder.bind(filmSerie)
        // Adding the listener to each item
        // With this implementation we can extract the listener method to another class
        holder.itemView.setOnClickListener{listener(filmSerie)}
    }

    fun updateList(filmsSeriesList: ArrayList<FilmSerie>):Unit {
        // This method updated the list in the Adapter
        this.fsList.clear()
        this.fsList.addAll(filmsSeriesList)
    }

    fun filterByName(filmsSeries: List<FilmSerie>){
        // This method is used to filter with the SearchView
        this.fsList.clear()
        this.fsList.addAll(filmsSeries)
        //notifyDataSetChanged()
    }

}