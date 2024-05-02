package com.enriquepalmadev.data_layer.feature.comics.utils

import com.enriquepalmadev.data_layer.feature.comics.dto.ApiError
import com.enriquepalmadev.data_layer.feature.comics.dto.ComicDto
import com.enriquepalmadev.data_layer.feature.comics.dto.Failure
import com.enriquepalmadev.data_layer.feature.comics.dto.Unauthorized
import com.enriquepalmadev.data_layer.feature.comics.dto.UnknownHostError
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
        id = id ?: -1,
        title = title.orEmpty(),
        description = description,
        pageCount = pageCount ?: -1,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}

fun Failure.toFailureDomain(): FailureDomain {
    return when (this) {
        is ApiError -> FailureDomain.ApiError(code = code, message = message)
        Unauthorized -> FailureDomain.Unauthorized
        UnknownHostError -> FailureDomain.UnknownHostError
    }
}