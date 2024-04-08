package com.enriquepalmadev.appmarvel.data.utils

import com.enriquepalmadev.appmarvel.data.model.ComicModelDto
import com.enriquepalmadev.appmarvel.domain.model.ComicModel

fun List<ComicModelDto>.dtoToComicListModel() : List<ComicModel> {
    return this.map {
        it.dtoToComicModel()
    }
}

private fun ComicModelDto.dtoToComicModel() : ComicModel {
    return ComicModel(
        id = id.toString(),
        title = title,
        published = published,
        writer = writer,
        penciler = penciler,
        description = description,
        price = price.toString(),
        image = image
    )
}