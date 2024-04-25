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
        return CharactersViewHolder(binding, this)
    }

    override fun getItemCount(): Int = characterList.size


    //This method paint for each item the viewHolder
    /*
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character: CharacterModel = characterList[position]
        holder.bind(character, listener)

    }
     */
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character.id, listener)
    }

    fun getCharacterById(characterId: Int): CharacterModel? {
        return characterList.find { it.id == characterId }
    }
}
