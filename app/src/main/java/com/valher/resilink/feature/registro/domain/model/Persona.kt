package com.valher.resilink.feature.registro.domain.model

data class Persona (
    val id: String? = null,
    val numeroCasa: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val contrasena: String,
    val numeroDocumento: String,
    val tipoDocumento: String,
    val codigoAcceso: String,
    val fechaNacimiento: String,
    val activo: Boolean,
    val numerocasa: String
)