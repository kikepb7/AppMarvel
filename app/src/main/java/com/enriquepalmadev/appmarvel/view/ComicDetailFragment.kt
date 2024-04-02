package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicDetailBinding
import com.enriquepalmadev.appmarvel.model.Comic
import com.enriquepalmadev.appmarvel.viewmodel.ComicDetailViewModel
import com.enriquepalmadev.appmarvel.viewmodel.DetailState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ComicDetailFragment : Fragment() {

    companion object {
        val KEY_ID = "id"
    }

    private val comicId by lazy { arguments?.getLong(KEY_ID) }
    private lateinit var binding: FragmentComicDetailBinding
    private val viewModel: ComicDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        comicId?.let { id ->
            viewModel.getComicDetail(id)
        }
    }

    private fun initObserver() {
        viewModel.state.onEach { state ->
            when (state) {
                is DetailState.ComicDetail -> {
                    showComicDetail(state.comic)
                }

                DetailState.Error -> {}
                DetailState.Loading -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showComicDetail(comic: Comic) {
        binding.apply {
            Glide.with(ivComic.context)
                .load(comic.image)
                .apply(
                    RequestOptions()
                        .error(R.drawable.error_404)
                )
                .into(ivComic)


            comicId.text = comic.id.toString()
            tvTitle.text = comic.title
            tvDescription.text = comic.description
            tvPrice.text = comic.price.toString()
        }
    }
}