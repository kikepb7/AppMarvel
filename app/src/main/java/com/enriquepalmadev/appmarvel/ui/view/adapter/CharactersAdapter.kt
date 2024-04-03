package com.enriquepalmadev.appmarvel.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import  com.enriquepalmadev.appmarvel.domain.model.Character

class CharactersAdapter(
    private val characterList: List<Character>,
    private val listener: (Character) -> Unit
) : RecyclerView.Adapter<CharactersViewHolder>() {

    //private var characterList = arrayListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount(): Int = characterList.size

    //This method paint for each item the viewHolder
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character: Character = characterList[position]
        holder.bind(character, listener)

    }

}
