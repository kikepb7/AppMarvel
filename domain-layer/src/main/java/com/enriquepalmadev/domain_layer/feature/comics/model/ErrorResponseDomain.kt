package com.enriquepalmadev.domain_layer.feature.comics.model

sealed class FailureDomain {
    data class ApiError(val code: Int, val message: String) : FailureDomain()
    data object Unauthorized : FailureDomain()
    data object UnknownHostError : FailureDomain()
}

sealed class Either<out L, out R> {
    data class Failure<out L>(val error: L): Either<L, Nothing>()
    data class Success<out R>(val data: R): Either<Nothing, R>()
}