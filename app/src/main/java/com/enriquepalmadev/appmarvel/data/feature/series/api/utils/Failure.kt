package com.enriquepalmadev.appmarvel.data.feature.series.api.utils

sealed class Failure()
data class CustomError(val code: String, val msg: String): Failure()
data object UnauthorizedError: Failure()
data object EmptyError: Failure()
data object UnknownHostError: Failure()
data object CoroutineError: Failure()

