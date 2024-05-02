package com.enriquepalmadev.appmarvel.ui.feature.comics.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enriquepalmadev.appmarvel.databinding.ItemComicsBinding
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel
import com.enriquepalmadev.appmarvel.ui.feature.comics.view.utils.loadImage

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