package com.enriquepalmadev.appmarvel.domain.model

import java.io.Serializable

data class ComicModel(
    val id: Long,
    val title: String,
    val published: String,
    val writer: String,
    val penciler: String,
    // val coverArtist: String, TODO --> Implements into ComicProvider and the comic's view
    val description: String,
    val price: Double, // TODO --> Remove price from everywhere in recyclerview and detail screen
    val image: String
): Serializable
