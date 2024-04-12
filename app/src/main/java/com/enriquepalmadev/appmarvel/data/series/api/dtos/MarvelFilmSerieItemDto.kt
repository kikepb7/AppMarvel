package com.enriquepalmadev.appmarvel.data.series.api.dtos

import com.google.gson.annotations.SerializedName

data class MarvelFilmSerieItemDto(
    @SerializedName("characters")
    val characters: MarvelCharactersDto,
    @SerializedName("comics")
    val comics: MarvelComicsDto,
    @SerializedName("creators")
    val creators: MarvelCreatorsDto,
    @SerializedName("description")
    val description: String,
    @SerializedName("endYear")
    val endYear: Int,
    @SerializedName("events")
    val events: MarvelEventsDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("startYear")
    val startYear: Int,
    @SerializedName("stories")
    val stories: MarvelStoriesDto,
    @SerializedName("thumbnail")
    val thumbnail: MarvelThumbnailDto,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("urls")
    val urls: List<MarvelUrlDto>
)