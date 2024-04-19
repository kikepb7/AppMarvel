package com.enriquepalmadev.appmarvel.data.feature.comics.utils

import com.enriquepalmadev.appmarvel.data.feature.comics.dto.ComicDto
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel

// Take the actual list (List<ResultDto>) and map it to a new ComicModel list
fun List<ComicDto>.dtoToComicListModel(): List<ComicModel> {
    return this.map {
        it.dtoToComicModel()
    }
}

// Take the ResultDto object and transform it to a ComicModel object
fun ComicDto.dtoToComicModel(): ComicModel {
    return ComicModel(
        id = id ?: -1,
        title = title ?: "",
        description = description,
        pageCount = pageCount ?: -1,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}