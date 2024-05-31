package com.enriquepalmadev.data_layer.feature.series.mapper

import com.enriquepalmadev.data_layer.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.domain_layer.feature.series.models.FilmSerieModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface IFilmSerieMapper {

    @Mapping(source = "thumbnail.extension", target = "thumbnailExt")
    @Mapping(source = "thumbnail.path", target = "thumbnailPath")
    fun marvelFilmSerieItemDtoToFilmSerieModel (marvelFilmSerieItemDto: MarvelFilmSerieItemDto): FilmSerieModel
}