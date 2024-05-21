package com.enriquepalmadev.ui_layer.feature.character.view.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.commons.loadImage
import com.enriquepalmadev.ui_layer.databinding.ItemSuperheroBinding


class CharactersViewHolder(
    private val binding: ItemSuperheroBinding
) : ViewHolder(binding.root)
{
    fun bind(character: CharacterModel, listener: (Int) -> Unit){

        binding.apply {
            imageButton.loadImage(character.thumbnailDTO)
            tvTexto.text = character.name
            imageButton.setOnClickListener{
                listener.invoke(character.id)
            }
        }
    }
}