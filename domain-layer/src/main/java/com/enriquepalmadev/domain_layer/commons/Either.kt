package com.enriquepalmadev.domain_layer.commons

sealed class Either<out L, out R> {
    data class Error<out L>(val error: L) : Either<L, Nothing>()
    data class Success<out R>(val data: R) : Either<Nothing, R>()
}
fun <T> eitherSuccess(data : T) = Either.Success(data)

