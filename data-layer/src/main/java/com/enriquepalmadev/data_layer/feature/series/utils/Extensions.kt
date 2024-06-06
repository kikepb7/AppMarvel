package com.enriquepalmadev.data_layer.feature.series.utils

import com.enriquepalmadev.data_layer.feature.series.database.entities.SerieEntity
import com.enriquepalmadev.data_layer.feature.series.failure.DefaultErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.EmptyErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.FailureData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain
import com.enriquepalmadev.domain_layer.feature.series.model.FilmSerieModel

fun FailureData.toFailureDomain(): FailureDomain {
    return when (this) {
        DefaultErrorData -> FailureDomain.DefaultErrorDomain
        is CustomErrorData -> FailureDomain.CustomErrorDomain(code = code, msg = msg)
        EmptyErrorData -> FailureDomain.EmptyErrorDomain
        UnauthorizedErrorData -> FailureDomain.UnauthorizedErrorDomain
        UnknownHostErrorData -> FailureDomain.UnknownHostErrorDomain
    }
}

fun List<SerieEntity>.entityToSerieListModel(): List<FilmSerieModel> {
    return this.map {
        it.entityToSerieModel()
    }
}

fun SerieEntity.entityToSerieModel(): FilmSerieModel {
    return FilmSerieModel(
        id = idSerie,
        title = titleSerie,
        description = descriptionSerie,
        thumbnailPath = thumbnailPathSerie,
        thumbnailExt = thumbnailExtSerie,
        startYear = startYearSerie,
        isFav = isFav
    )
}

fun FilmSerieModel.modelToSerieEntity(): SerieEntity {
    return SerieEntity(
        idSerie = id,
        titleSerie = title,
        descriptionSerie = description,
        thumbnailPathSerie = thumbnailPath,
        thumbnailExtSerie = thumbnailExt,
        startYearSerie = startYear,
        isFav = isFav
    )
}


fun List<FilmSerieModel>.modelToSerieListEntity(): List<SerieEntity> {
    return this.map {
        it.modelToSerieEntity()
    }
}