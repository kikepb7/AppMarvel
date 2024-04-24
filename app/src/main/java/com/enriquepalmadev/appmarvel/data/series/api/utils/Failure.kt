package com.enriquepalmadev.appmarvel.data.series.api.utils

sealed class Failure()
data class CustomError(val code: Int, val msg: String): Failure()
data object UnauthorizedError: Failure()
data object EmptyError: Failure()
data object UnknownHostError: Failure()

