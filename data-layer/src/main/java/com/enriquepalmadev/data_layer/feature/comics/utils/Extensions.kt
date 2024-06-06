package com.enriquepalmadev.data_layer.feature.comics.utils

import com.enriquepalmadev.data_layer.feature.comics.dto.ComicDto
import com.enriquepalmadev.data_layer.feature.comics.dto.FailureDto
import com.enriquepalmadev.domain_layer.feature.comics.model.ComicModel
import com.enriquepalmadev.domain_layer.feature.comics.model.FailureDomain

// Take the actual list (List<ResultDto>) and map it to a new ComicModel list
fun List<ComicDto>.dtoToComicListModel(): List<ComicModel> {
    return this.map {
        it.dtoToComicModel()
    }
}

// Take the ResultDto object and transform it to a ComicModel object
fun ComicDto.dtoToComicModel(): ComicModel {
    return ComicModel(
        id = id,
        title = title.orEmpty(),
        description = description,
        pageCount = pageCount ?: -1,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}

fun FailureDto.toFailureDomain(): FailureDomain {
    return when (this.code) {
        400 -> FailureDomain.UnknownHostError
        401 -> FailureDomain.Unauthorized
        else -> FailureDomain.ApiError
    }
}