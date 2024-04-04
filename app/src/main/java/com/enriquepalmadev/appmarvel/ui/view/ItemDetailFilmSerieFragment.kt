package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enriquepalmadev.appmarvel.databinding.ItemDetailsFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.model.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.view.utils.loadImage

class ItemDetailFilmSerieFragment : Fragment() {
    private lateinit var bindingItemDetailsFilmsSeries: ItemDetailsFilmsSeriesBinding
    private var filmSerieModel: FilmSerieModel? = null

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
        filmSerieModel = filmsSeriesData?.getSerializable("objectFilmOrSerie") as FilmSerieModel?
        // This function is deprecated but the other function that is available can be used only from API level 33
    }

    private fun renderUi(){
        bindingItemDetailsFilmsSeries.detailFimsSeriesName.text = filmSerieModel?.name
        filmSerieModel?.cover?.let { myCover ->
            bindingItemDetailsFilmsSeries.detailImageFilmsSeries.loadImage(myCover)
        }
        bindingItemDetailsFilmsSeries.detailFimsSeriesDescription.text = filmSerieModel?.description
    }
}