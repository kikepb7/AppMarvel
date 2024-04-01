package com.enriquepalmadev.appmarvel.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.ItemComicsBinding
import com.enriquepalmadev.appmarvel.model.Comic

class ComicsViewHolder(view: View) : ViewHolder(view) {

    val binding = ItemComicsBinding.bind(view)

    fun render(
        comic: Comic,
        onClickListener: (Comic) -> Unit
    ) {
        Glide.with(binding.ibImageComic.context)
            .load(comic.image)
            .apply(
                RequestOptions()
                    .error(R.drawable.error_404)
            )
            .into(binding.ibImageComic)

        binding.tvComicID.text = comic.id.toString()
        binding.tvComicTitle.text = comic.title
        binding.tvPrice.text = comic.price.toString()

        itemView.setOnClickListener {
            onClickListener(comic)
        }
    }
}