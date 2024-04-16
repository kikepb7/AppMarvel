package com.enriquepalmadev.appmarvel.data.series.api.utils

data class GenericException(val code: Int, val msg: String): Failure()

data object UnauthorizedError: Failure()

sealed class Failure()