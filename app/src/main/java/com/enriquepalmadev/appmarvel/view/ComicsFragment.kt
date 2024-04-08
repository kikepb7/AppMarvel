package com.enriquepalmadev.appmarvel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentComicsBinding
import com.enriquepalmadev.appmarvel.viewmodel.ComicsViewModel

class ComicsFragment : Fragment() {
    private lateinit var binding: FragmentComicsBinding
    private val comicsViewModel: ComicsViewModel by viewModels()

    // Part of fragments life cycle. It's call when fragments is being created
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicsBinding.inflate(inflater, container, false)
        val view = binding.root

        comicsViewModel.imageModel.observe(viewLifecycleOwner) { currentImage ->

            // Using Glide library
            Glide.with(this)
                .load(currentImage.image)
                .apply(
                    RequestOptions().fallback(R.drawable.ic_launcher_background)
                        .error(R.drawable.error_404)
                )
                .into(binding.imgComic)


            // Using Coil library
            //binding.imgComic.load("https://www.milcomics.com/1306405-large_default/spiderman-01.jpg")
        }


        // If you touch the screen the image will change automatically
        view.setOnClickListener {
            comicsViewModel.nextComic()
        }

        return view
    }
}