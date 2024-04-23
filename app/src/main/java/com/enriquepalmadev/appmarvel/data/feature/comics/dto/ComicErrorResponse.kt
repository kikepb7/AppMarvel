package com.enriquepalmadev.appmarvel.data.feature.comics.dto

sealed class Failure

data class ApiError(val code: Int, val message: String): Failure()
data object Unauthorized: Failure()

sealed class Either<out L, out R> {
    data class Failure<out L>(val error: L): Either<L, Nothing>()
    data class Success<out R>(val data: R): Either<Nothing, R>()
}