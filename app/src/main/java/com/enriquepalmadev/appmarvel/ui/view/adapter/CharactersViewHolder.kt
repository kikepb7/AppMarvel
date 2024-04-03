package com.enriquepalmadev.appmarvel.ui.view.adapter

import androidx.navigation.Navigation.findNavController
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

        //To put on favourites or not ->
        binding.ivFavourites.setOnClickListener{
            if(binding.ivFavourites.contentDescription == "on"){
                binding.ivFavourites.setImageResource(R.drawable.ic_border_favorite_24dp)
                binding.ivFavourites.contentDescription = "off"

                //More code in process
            }else if (binding.ivFavourites.contentDescription == "off"){
                binding.ivFavourites.setImageResource(R.drawable.ic_full_favorite_24dp)
                binding.ivFavourites.contentDescription = "on"

                //More code in process
            }
        }

    }


    /*fun render(
        character: Character,
        onClickListener: (Character) -> Unit
    ){

        binding.imageButton.loadImage(character.image)

        /*binding.imageButton.load(character.image) {
            crossfade(true)
            size(800, 800)
            scale(Scale.FILL)
            placeholder(R.drawable.cargando)
        }*/

        binding.tvTexto.text = character.nombre

        itemView.setOnClickListener{
            onClickListener(character)
        }
    }*/


}