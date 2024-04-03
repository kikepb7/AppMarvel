package com.enriquepalmadev.appmarvel.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemSuperheroBinding
import com.enriquepalmadev.appmarvel.model.Character
import com.enriquepalmadev.appmarvel.view.utils.loadImage


class CharactersViewHolder(view: View) : ViewHolder(view)
{
    val binding = ItemSuperheroBinding.bind(view)

    fun render(
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
    }
}