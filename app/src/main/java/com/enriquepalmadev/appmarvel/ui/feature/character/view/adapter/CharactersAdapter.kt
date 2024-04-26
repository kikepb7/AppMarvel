package com.enriquepalmadev.appmarvel.ui.feature.character.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel

class CharactersAdapter(
    private var characterList: List<CharacterModel>,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character: CharacterModel = characterList[position]
        holder.bind(character, listener)

    }
}
