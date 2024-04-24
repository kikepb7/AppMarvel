package com.enriquepalmadev.appmarvel.ui.feature.comics.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.databinding.ItemComicsBinding
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.utils.loadImage

class ComicsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemComicsBinding.bind(view)

    fun render(
        comicModel: ComicModel,
        onClickListener: (ComicModel) -> Unit
    ) {
        binding.apply {
            ibImageComic.loadImage(comicModel.thumbnail)
            tvComicTitle.text = comicModel.title
            ibImageComic.setOnClickListener {
                onClickListener(comicModel)
            }
        }
    }
}