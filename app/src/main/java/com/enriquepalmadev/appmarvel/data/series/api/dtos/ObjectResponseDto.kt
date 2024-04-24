package com.enriquepalmadev.appmarvel.data.series.api.dtos
import com.google.gson.annotations.SerializedName

data class ObjectResponseDto(
    val code: Int,
    val data: DataDto
)

data class DataDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<MarvelFilmSerieItemDto>,
    val total: Int
)

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

data class MarvelCharactersDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class MarvelComicsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemDto>,
    val returned: Int
)

data class MarvelItemDto(
    val name: String,
    val resourceURI: String
)

data class MarvelCreatorsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemXDto>,
    val returned: Int
)

data class MarvelItemXDto(
    val name: String,
    val resourceURI: String,
    val role: String
)

data class MarvelEventsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class MarvelStoriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItemXXDto>,
    val returned: Int
)

data class MarvelItemXXDto(
    val name: String,
    val resourceURI: String,
    val type: String
)

data class MarvelThumbnailDto(
    val extension: String,
    val path: String
)

data class MarvelUrlDto(
    val type: String,
    val url: String
)