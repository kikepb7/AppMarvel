package com.enriquepalmadev.appmarvel.domain.mapper
import com.enriquepalmadev.appmarvel.data.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.domain.models.FilmSerieModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface IFilmSerieMapper {

    @Mapping(source = "thumbnail.extension", target = "thumbnailExt")
    @Mapping(source = "thumbnail.path", target = "thumbnailPath")
    fun marvelFilmSerieItemDtoToFilmSerieModel (marvelFilmSerieItemDto: MarvelFilmSerieItemDto): FilmSerieModel
}