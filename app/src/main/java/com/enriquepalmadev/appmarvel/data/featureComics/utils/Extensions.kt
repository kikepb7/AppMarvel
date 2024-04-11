package com.enriquepalmadev.appmarvel.data.featureComics.utils

import com.enriquepalmadev.appmarvel.data.featureComics.model.ResultDto
import com.enriquepalmadev.appmarvel.domain.featureComics.model.ComicModel

fun List<ResultDto>.dtoToComicListModel() : List<ComicModel> {
    return this.map {
        it.dtoToComicModel()
    }
}

fun ResultDto.dtoToComicModel() : ComicModel {
    return ComicModel(
        id = id ?: -1,
        title = title ?: "",
        description = description,
        pageCount = pageCount ?: -1,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}