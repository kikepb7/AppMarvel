package com.enriquepalmadev.appmarvel.ui.feature.character.view.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.ui.feature.character.view.utils.loadImage


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