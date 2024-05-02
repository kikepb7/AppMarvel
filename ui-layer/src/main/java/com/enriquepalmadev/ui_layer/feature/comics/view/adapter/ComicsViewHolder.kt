package com.enriquepalmadev.ui_layer.feature.comics.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.ui_layer.databinding.ItemComicsBinding
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.loadImage

class ComicsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemComicsBinding.bind(view)

    fun render(
        comic: ComicModel,
        onClickListener: (Int) -> Unit
    ) {
        binding.apply {
            ibImageComic.apply {
                loadImage(comic.thumbnail)
                setOnClickListener {
                    onClickListener.invoke(comic.id)
                }
            }
            tvComicTitle.text = comic.title
        }
    }
}