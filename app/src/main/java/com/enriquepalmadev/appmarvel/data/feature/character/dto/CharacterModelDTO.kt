package com.enriquepalmadev.appmarvel.data.feature.character.dto

data class CharacterModelDTO(
    val id: Int,
    val name: String,
    val description: String,
    val image: String
)
data class CharacterResponseDTO<T>(
    // val code: String,
    val data: DataDTO?
    //val etag: String,
)

data class DataDTO(
    val count: String,
    val limit: String,
    val offset: String,
    val results: List<ResultDTO>,
    val total: String
)

data class ResultDTO(
    val description: String,
    val id: String,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val thumbnail: ThumbnailDTO,
    val urls: List<UrlDTO>
)

data class ThumbnailDTO(
    val extension: String,
    val path: String
)
data class UrlDTO(
    val type: String,
    val url: String
)