package com.enriquepalmadev.appmarvel.data.series.api.utils

data class AnotherError(val code: Int, val msg: String): Failure()

data object UnauthorizedError: Failure()

sealed class Failure()