package com.enriquepalmadev.appmarvel.domain.mapper
import com.enriquepalmadev.appmarvel.data.api.dtos.MarvelFilmSerieItemDto
import com.enriquepalmadev.appmarvel.domain.models.FilmSerie
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface FilmSerieMapper {

    @Mapping(source = "thumbnail.extension", target = "thumbnailExt")
    @Mapping(source = "thumbnail.path", target = "thumbnailPath")
    fun marvelFilmSerieItemDtoToFilmSerie (marvelFilmSerieItemDto: MarvelFilmSerieItemDto): FilmSerie
}