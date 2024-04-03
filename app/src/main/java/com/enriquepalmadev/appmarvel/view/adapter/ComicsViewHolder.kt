package com.enriquepalmadev.appmarvel.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemComicsBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.view.utils.loadImage

class ComicsViewHolder(view: View) : ViewHolder(view) {

    val binding = ItemComicsBinding.bind(view)

    fun render(
        comic: Comic,
        onClickListener: (Comic) -> Unit
    ) {
        binding.ibImageComic.loadImage(comic.image)

        binding.tvComicID.text = comic.id.toString()
        binding.tvComicTitle.text = comic.title
        binding.tvPrice.text = comic.price.toString()

        itemView.setOnClickListener {
            onClickListener(comic)
        }
    }
}