package com.enriquepalmadev.appmarvel.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.R
import  com.enriquepalmadev.appmarvel.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersAdapter(
    private val charactersList: List<Character>,
    private val onClickListener: (Character) -> Unit
) : RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return CharactersViewHolder(layoutInflater.inflate(R.layout.item_superhero, parent, false))
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = charactersList[position]

        holder.render(item, onClickListener)
    }

}