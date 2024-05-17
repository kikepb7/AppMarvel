package com.enriquepalmadev.ui_layer.feature.character.view.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.ItemSuperheroBinding
import com.enriquepalmadev.ui_layer.feature.character.view.utils.loadImage


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

        //Favourites function
        var currentImage = R.drawable.ic_border_favorite_24dp
        //To put on favourites or not ->
        binding.ivFavourites.setOnClickListener{
            if(currentImage == R.drawable.ic_full_favorite_24dp){
                binding.ivFavourites.setImageResource(R.drawable.ic_border_favorite_24dp)
                currentImage = R.drawable.ic_border_favorite_24dp
                //More code in process
            }else{
                binding.ivFavourites.setImageResource(R.drawable.ic_full_favorite_24dp)
                currentImage = R.drawable.ic_full_favorite_24dp
                //More code in process
            }
        }
    }
}