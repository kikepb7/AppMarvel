package com.enriquepalmadev.domain_layer.feature.series.utils

sealed class ResponseEither<out L, out R> {
    data class Failure<out L>(val failure: L): ResponseEither<L, Nothing>()
    data class Success<out R>(val success: R): ResponseEither<Nothing, R>()
}