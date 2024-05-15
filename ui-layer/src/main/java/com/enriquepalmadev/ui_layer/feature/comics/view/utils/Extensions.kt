package com.enriquepalmadev.ui_layer.feature.comics.view.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenError
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenHeader
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenItemModel
import com.enriquepalmadev.ui_layer.feature.comics.view.model.ComicListScreenTitle

fun ImageView.loadImage(image: String) {
    Glide.with(this)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.error_404)
        )
        //.placeholder(R.drawable.progress_animation) // TODO --> Adapt loader
        .into(this)
}

fun Button.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

fun ImageButton.navigateTo(action: Int) {
    setOnClickListener {
        findNavController().navigate(action)
    }
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.gone() {
    this.visibility = GONE
}

fun toComicListModelHeader(): ComicListScreenHeader {
    return ComicListScreenHeader(
        imageLogo = R.drawable.marvel_comics_logo,
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        placeholderText = "Buscar...",
        onClickSearch = {}
    )
}

enum class ComicListType {
    ALL_COMICS,
    FAVORITES
}

fun List<ComicModel>.toComicListModel(
    comicListType: ComicListType
): ComicListModel {
    return when (comicListType) {
        ComicListType.ALL_COMICS -> {
            ComicListModel(
                comicListScreenTitle = ComicListScreenTitle(
                    icon = R.drawable.ironman,
                    title = "TODOS LOS COMICS"
                ),
                comicsModel = this.map {
                    it.toComicModel()
                }
            )
        }

        ComicListType.FAVORITES -> {
            ComicListModel(
                comicListScreenTitle = ComicListScreenTitle(
                    icon = R.drawable.ic_solid_heart,
                    title = "COMICS FAVORITOS"
                ),
                comicsModel = this.map {
                    it.toComicModel()
                }
            )
        }
    }
}

fun ComicModel.toComicModel(): ComicListScreenItemModel {
    return ComicListScreenItemModel(
        comic = this
    )
}

fun FailureDomain.toComicListScreenError(): ComicListScreenError {
    return when (this) {
        is FailureDomain.ApiError -> {
            ComicListScreenError(
                image = R.drawable.comic_detail_error,
                errorMsg = R.string.error_code.toString()
            )
        }
        FailureDomain.Unauthorized -> {
            ComicListScreenError(
                image = R.drawable.comic_detail_error,
                errorMsg = "ERROR 401 \nYou are not authorized!"
            )
        }
        FailureDomain.UnknownHostError -> {
            ComicListScreenError(
                image = R.drawable.comic_detail_error,
                errorMsg = R.string.error_code.toString()
            )
        }
    }
}