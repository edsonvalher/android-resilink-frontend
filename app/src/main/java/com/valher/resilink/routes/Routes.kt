package com.valher.resilink.routes

sealed class Routes(val route: String) {
    object Registrarse : Routes("registrarse")
}