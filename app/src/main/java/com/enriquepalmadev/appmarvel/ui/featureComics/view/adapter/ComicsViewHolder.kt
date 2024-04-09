package com.enriquepalmadev.appmarvel.ui.featureComics.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.databinding.ItemComicsBinding
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.featureComics.view.utils.loadImage

class ComicsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemComicsBinding.bind(view)

    fun render(
        comicModel: ComicModel,
        onClickListener: (ComicModel) -> Unit
    ) {
        binding.ibImageComic.loadImage(comicModel.thumbnail)

        //binding.tvComicID.text = comicModel.id.toString()
        binding.tvComicTitle.text = comicModel.title
        //binding.tvPrice.text = comicModel.price.toString()

        binding.ibImageComic.setOnClickListener {
            onClickListener(comicModel)
        }
    }
}