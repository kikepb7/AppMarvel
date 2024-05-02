package com.enriquepalmadev.ui_layer.feature.comics.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.ui_layer.R

class ComicsAdapter(
    private var comicsList: List<ComicModel>,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {

        // We should never pass the context to a RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)

        return ComicsViewHolder(layoutInflater.inflate(R.layout.item_comics, parent, false))
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.render(comicsList[position], onClickListener)
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    fun updateComics(comicUpdated: List<ComicModel>) {
        comicsList = comicUpdated
        notifyDataSetChanged()
    }
}