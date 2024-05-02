package com.enriquepalmadev.data_layer.feature.comics.dto

data class ResponseMarvelDto (
    val code: Int? = null,
    val data: DataDto? = null,
    val etag: String? = null
)

data class DataDto(
    val count: Int? = null,
    val limit: Int? = null,
    val offset: Int? = null,
    val results: List<ComicDto>? = null,
    val total: Int? = null
)

data class ComicDto(
    val description: String? = null,
    val id: Int? = null,
    val pageCount: Int? = null,
    val thumbnail: ThumnailDto? = null,
    val title: String? = null,
    val characters: CharactersDto,
    val collectedIssues: List<Any>,
    val collections: List<Any>,
    val creators: CreatorsDto? = null,
    val dates: List<DateDto>? = null,
    val diamondCode: String,
    val digitalId: Int,
    val ean: String,
    val events: EventsDto,
    val format: String,
    val images: List<Any>,
    val isbn: String,
    val issn: String,
    val issueNumber: Int,
    val modified: String,
    val prices: List<PriceDto>? = null,
    val resourceURI: String,
    val series: SeriesDto,
    val stories: Stories,
    val textObjects: List<Any>,
    val upc: String,
    val urls: List<UrlDto>,
    val variantDescription: String,
    val variants: List<VariantDto>
)

data class ThumnailDto(
    val extension: String,
    val path: String
)

data class CharactersDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class CreatorsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemDto>,
    val returned: Int
)

data class DateDto(
    val date: String,
    val type: String
)

data class EventsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class ItemDto(
    val name: String,
    val resourceURI: String,
    val role: String
)

data class ItemXDto(
    val name: String,
    val resourceURI: String,
    val type: String
)

data class PriceDto(
    val price: Float,
    val type: String
)

data class SeriesDto(
    val name: String,
    val resourceURI: String
)

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXDto>,
    val returned: Int
)

data class UrlDto(
    val type: String,
    val url: String
)

data class VariantDto(
    val name: String,
    val resourceURI: String
)