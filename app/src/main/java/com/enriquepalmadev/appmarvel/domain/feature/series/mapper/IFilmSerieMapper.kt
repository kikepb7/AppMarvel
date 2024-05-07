package com.enriquepalmadev.appmarvel.domain.feature.series.mapper
import com.enriquepalmadev.appmarvel.data.feature.series.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface IFilmSerieMapper {

    @Mapping(source = "thumbnail.extension", target = "thumbnailExt")
    @Mapping(source = "thumbnail.path", target = "thumbnailPath")
    fun marvelFilmSerieItemDtoToFilmSerieModel (marvelFilmSerieItemDto: MarvelFilmSerieItemDto): FilmSerieModel
}