package com.enriquepalmadev.appmarvel.ui.feature.character.view.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.domain.feature.character.model.CharacterModel
import com.enriquepalmadev.appmarvel.ui.feature.character.view.utils.loadImage


class CharactersViewHolder(
    private val binding: ItemSuperheroBinding,
    private val adapter: CharactersAdapter
) : ViewHolder(binding.root)
{
    //fun bind(character: CharacterModel, listener: (CharacterModel) -> Unit){
    fun bind(characterId: Int, listener: (Int) -> Unit){
        val character = adapter.getCharacterById(characterId)
        character?.let{
            binding.imageButton.loadImage(it.thumbnailDTO)
            binding.tvTexto.text = it.name
        }

        //Go to character detail
        binding.imageButton.setOnClickListener {
            listener.invoke(characterId)
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