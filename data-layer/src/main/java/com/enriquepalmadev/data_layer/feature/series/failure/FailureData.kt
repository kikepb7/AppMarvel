package com.enriquepalmadev.data_layer.feature.series.failure

sealed class FailureData()
data class CustomErrorData(val code: String, val msg: String): FailureData()
data object UnauthorizedErrorData: FailureData()
data object EmptyErrorData: FailureData()
data object UnknownHostErrorData: FailureData()
data object CoroutineErrorData: FailureData()
