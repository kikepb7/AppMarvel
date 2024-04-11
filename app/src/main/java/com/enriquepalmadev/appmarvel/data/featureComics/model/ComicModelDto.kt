package com.enriquepalmadev.appmarvel.data.featureComics.model

data class ResponseMarvelDto<T> (
    //val code: Int? = null,
    val data: DataDto? = null,
    //val etag: String? = null
)

data class DataDto(
    val count: Int? = null,
    val limit: Int? = null,
    val offset: Int? = null,
    val results: List<ResultDto>? = null,
    val total: Int? = null
)

data class ResultDto(
    val description: String? = null,
    val id: Int? = null,
    val pageCount: Int? = null,
    val thumbnail: ThumnailDto? = null,
    val title: String? = null,
    //val characters: Characters,
    //val collectedIssues: List<Any>,
    //val collections: List<Any>,
    //val creators: Creators? = null,
    //val dates: List<Date>? = null
    //val diamondCode: String,
    //val digitalId: Int,
    //val ean: String,
    //val events: Events,
    //val format: String,
    //val images: List<Any>,
    //val isbn: String,
    //val issn: String,
    //val issueNumber: Int,
    //val modified: String,
    //val prices: List<Price>? = null,
    //val resourceURI: String,
    //val series: Series,
    //val stories: Stories,
    //val textObjects: List<Any>,
    //val upc: String,
    //val urls: List<Url>,
    //val variantDescription: String,
    //val variants: List<Variant>
)

data class ThumnailDto(
    val extension: String,
    val path: String
)

/*
data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)

data class Date(
    val date: String,
    val type: String
)

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)

data class Item(
    val name: String,
    val resourceURI: String,
    val role: String
)

data class ItemX(
    val name: String,
    val resourceURI: String,
    val type: String
)

data class Price(
    val price: Int,
    val type: String
)

data class Series(
    val name: String,
    val resourceURI: String
)

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)

data class Url(
    val type: String,
    val url: String
)

data class Variant(
    val name: String,
    val resourceURI: String
)

data class ComicModelDto(
    val id: Long,
    val title: String,
    val published: String,
    val writer: String,
    val penciler: String,
    // val coverArtist: String,
    val description: String,
    val price: Double,
    val image: String
): Serializable
 */