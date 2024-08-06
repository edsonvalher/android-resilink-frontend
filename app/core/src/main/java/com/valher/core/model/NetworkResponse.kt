package com.valher.core.model

data class NetworkResponse<T>(
    val data: T?,
    val mensaje: String?,
    val estado : Boolean
)