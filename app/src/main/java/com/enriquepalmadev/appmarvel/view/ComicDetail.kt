package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.model.ComicProvider

class ComicDetail : Fragment() {

    companion object {
        val KEY_ID = "id"
    }

    /*

     */
    private val comicId by lazy { arguments?.getString(KEY_ID) }

    private lateinit var binding: FragmentComicDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicDetailBinding.inflate(layoutInflater)

//        val comicPrueba = arguments?.getSerializable("comic") as Comic




        val comic = comicId?.let {
            ComicProvider.comicsList.get(comicId!!.toInt())
        }

        Glide.with(binding.ivComic.context)
            .load(comic?.image)
            .apply(
                RequestOptions()
                    .error(R.drawable.error_404)
            )
            .into(binding.ivComic)

        binding.comicId.text = comic?.id.toString()
        binding.tvTitle.text = comic?.title
        binding.tvDescription.text = comic?.description
        binding.tvPrice.text = comic?.price.toString()

        return binding.root
    }
}