package com.enriquepalmadev.appmarvel.data.feature.comics.dto

sealed class Failure

data class ApiError(val code: Int, val message: String): Failure()
data object Unauthorized: Failure()
data object BadRequest: Failure()

sealed class Either<out L, out R> {
    data class Failure<out L>(val error: L): Either<L, Nothing>()
    data class Success<out R>(val data: R): Either<Nothing, R>()
}

/*
sealed class Either<out L, out R>
data class Success<R>(val data: R) : Either<Nothing, R>()
data class Failure<L>(val error: L) : Either<L, Nothing>()

sealed class Error {
    data class ApiError(val code: Int, val message: String) : Error()
    data object UnAuthorized : Error()
}


 */