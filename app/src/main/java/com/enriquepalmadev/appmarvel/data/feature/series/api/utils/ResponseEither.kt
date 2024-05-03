package com.enriquepalmadev.appmarvel.data.feature.series.api.utils

sealed class ResponseEither <out L, out R> {
    data class Failure <out L>(val l: L) : ResponseEither<L, Nothing>()
    data class Success <out R>(val r: R) : ResponseEither<Nothing, R>()
}