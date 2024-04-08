package com.enriquepalmadev.appmarvel.ui.view.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.domain.model.Character
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage


class CharactersViewHolder(private val binding: ItemSuperheroBinding) : ViewHolder(binding.root)
{
    val favCharacters : ArrayList<Int> = ArrayList<Int>()

    fun bind(character: Character, listener: (Character) -> Unit){
        binding.imageButton.loadImage(character.image)
        binding.tvTexto.text = character.nombre

        //Go to character detail
        binding.imageButton.setOnClickListener {
            listener.invoke(character)
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