package com.enriquepalmadev.appmarvel.data.api.dtos

data class MarvelFilmSerieItemDto(
    val characters: MarvelCharactersDto,
    val comics: MarvelComicsDto,
    val creators: MarvelCreatorsDto,
    val description: String,
    val endYear: Int,
    val events: MarvelEventsDto,
    val id: Int,
    val modified: String,
    val next: Any,
    val previous: Any,
    val rating: String,
    val resourceURI: String,
    val startYear: Int,
    val stories: MarvelStoriesDto,
    val thumbnail: MarvelThumbnailDto,
    val title: String,
    val type: String,
    val urls: List<MarvelUrlDto>
)