package com.enriquepalmadev.appmarvel.domain.featureComics.model

import java.io.Serializable

/*data class ComicModel(
    val id: String,
    val title: String,
    val published: String,
    val writer: String,
    val penciler: String,
    // val coverArtist: String, TODO --> Implements into ComicProvider and the comic's view
    val description: String,
    val price: String, // TODO --> Remove price from everywhere in recyclerview and detail screen
    val image: String
): Serializable*/

data class ComicModel(
    val id: Int,
    val title: String,
    val description: String?,
    val pageCount: Int,
    val thumbnail: String
): Serializable