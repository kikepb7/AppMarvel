package com.enriquepalmadev.domain_layer.feature.series.utils

sealed class FailureDomain {
    data class CustomError(val code: String, val msg: String): FailureDomain()
    data object UnauthorizedError: FailureDomain()
    data object EmptyError: FailureDomain()
    data object UnknownHostError: FailureDomain()
    data object CoroutineError: FailureDomain()
}

sealed class ResponseEitherDomain<out L, out R> {
    data class Failure<out L>(val error: L): ResponseEitherDomain<L, Nothing>()
    data class Success<out R>(val data: R): ResponseEitherDomain<Nothing, R>()
}
