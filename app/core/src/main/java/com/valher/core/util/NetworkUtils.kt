package com.valher.core.util

object NetworkUtils {
    fun isSuccessfulResponse(code: Int): Boolean {
        return code in 200..299
    }

    // Otras utilidades relacionadas con la red
}