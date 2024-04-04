package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.model.FilmsSeriesDataclass
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage

class ItemDetailsFilmsSeriesFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var filmsSeriesDataclass: FilmsSeriesDataclass? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingItemDetailsFilmsSeries = ItemDetailsFilmsSeriesBinding.inflate(inflater)

        retrieveFilmOrSerie()
        renderUi()

        return bindingItemDetailsFilmsSeries.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun retrieveFilmOrSerie(){
        val filmsSeriesData: Bundle? = arguments
        filmsSeriesDataclass = filmsSeriesData?.getSerializable("objectFilmOrSerie") as FilmsSeriesDataclass?
        // This function is deprecated but the other function that is available can be used only from API level 33
    }

    private fun renderUi(){
        bindingItemDetailsFilmsSeries.detailFimsSeriesName.text = filmsSeriesDataclass?.name
        filmsSeriesDataclass?.cover?.let { myCover ->
            bindingItemDetailsFilmsSeries.detailImageFilmsSeries.loadImage(myCover)
        }
        bindingItemDetailsFilmsSeries.detailFimsSeriesDescription.text = filmsSeriesDataclass?.description
    }
}