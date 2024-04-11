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
        binding.apply {
            ibImageComic.loadImage(comicModel.thumbnail)
            binding.tvComicTitle.text = comicModel.title
            binding.ibImageComic.setOnClickListener {
                onClickListener(comicModel)
            }
        }
    }
}