package com.enriquepalmadev.data_layer.feature.series.api.utils

sealed class ResponseEither <out L, out R> {
    data class Failure <out L>(val l: L) : ResponseEither<L, Nothing>()
    data class Success <out R>(val r: R) : ResponseEither<Nothing, R>()
}