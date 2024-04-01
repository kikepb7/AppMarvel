package com.enriquepalmadev.appmarvel.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.model.Comic

class ComicsAdapter(
    private val comicsList:List<Comic>,
    private val onClickListener: (Comic) -> Unit
) : RecyclerView.Adapter<ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {

        // We should never pass the context to a RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)

        return ComicsViewHolder(layoutInflater.inflate(R.layout.item_comics, parent, false))
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        val item = comicsList[position]

        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

}