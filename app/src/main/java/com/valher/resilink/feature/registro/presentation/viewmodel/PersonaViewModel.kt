package com.valher.resilink.feature.registro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.resilink.feature.registro.data.model.Persona
import com.valher.resilink.feature.registro.domain.usecase.RegistrarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val registrarUseCase: RegistrarUseCase
): ViewModel() {


    private val _persona = MutableStateFlow<Persona?>(null)
    open val persona: MutableStateFlow<Persona?> = _persona
    private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: MutableStateFlow<String?> = _errorMessage
    private val _isLoading = MutableStateFlow(false)
    open val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError

    private val _apellidoError = MutableStateFlow<String?>(null)
    val apellidoError: StateFlow<String?> = _apellidoError

    private val _numerocasaError = MutableStateFlow<String?>(null)
    val numerocasaError: StateFlow<String?> = _numerocasaError

    private val _codigoAccesoError = MutableStateFlow<String?>(null)
    val codigoAccesoError: StateFlow<String?> = _codigoAccesoError
    private val _telefonoError = MutableStateFlow<String?>(null)
    val telefonoError: StateFlow<String?> = _telefonoError

    private val _correoError = MutableStateFlow<String?>(null)
    val correoError: StateFlow<String?> = _correoError
    // Errores adicionales
    private val _recorreoError = MutableStateFlow<String?>(null)
    val recorreoError: StateFlow<String?> = _recorreoError

    private val _contrasenaError = MutableStateFlow<String?>(null)
    val contrasenaError: StateFlow<String?> = _contrasenaError

    private val _recontrasenaError = MutableStateFlow<String?>(null)
    val recontrasenaError: StateFlow<String?> = _recontrasenaError


    // Variables de estado para los campos del formulario
    private val _activo = MutableStateFlow(true)
    val activo: StateFlow<Boolean> = _activo

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _apellido = MutableStateFlow("")
    val apellido: StateFlow<String> = _apellido

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo
    private val _recorreo = MutableStateFlow("")
    val recorreo: StateFlow<String> = _recorreo

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono

    private val _contrasena = MutableStateFlow("")
    val contrasena: StateFlow<String> = _contrasena

    private val _recontrasena = MutableStateFlow("")
    val recontrasena: StateFlow<String> = _recontrasena

    private val _numeroDocumento = MutableStateFlow("")
    val numeroDocumento: StateFlow<String> = _numeroDocumento

    private val _tipoDocumento = MutableStateFlow("")
    val tipoDocumento: StateFlow<String> = _tipoDocumento

    private val _codigoAcceso = MutableStateFlow("")
    val codigoAcceso: StateFlow<String> = _codigoAcceso

    private val _fechaNacimiento = MutableStateFlow("")
    val fechaNacimiento: StateFlow<String> = _fechaNacimiento

    private val _numerocasa = MutableStateFlow("")
    val numerocasa: StateFlow<String> = _numerocasa

    private val _fotoUri = MutableStateFlow("")
    val fotoUri: StateFlow<String> = _fotoUri

    private val _documentoUri = MutableStateFlow("")
    val documentoUri: StateFlow<String> = _documentoUri

    private val _condominioId = MutableStateFlow("")
    val condominioId: StateFlow<String> = _condominioId

    private val _sectorId = MutableStateFlow("")
    val sectorId: StateFlow<String> = _sectorId
    //validaciones
    fun validarCondominio(): Boolean {
        return _condominioId.value.isNotEmpty()
    }
    fun validarSector(): Boolean {
        return _sectorId.value.isNotEmpty()
    }
    fun validarNombre(): Boolean {
        val nombrePattern = Regex("^[A-Z][a-zA-ZáéíóúÁÉÍÓÚ]+$")
        val isValid = _nombre.value.length >= 3 && nombrePattern.matches(_nombre.value)
        _nombreError.value = if (!isValid) "El nombre no es válido" else null
        return isValid
    }
    fun validarApellido(): Boolean {
        val apellidoPattern = Regex("^[A-Z][a-zA-ZáéíóúÁÉÍÓÚ]+$")
        val isValid = _apellido.value.length >= 3 && apellidoPattern.matches(_apellido.value)
        _apellidoError.value = if (!isValid) "El apellido no es válido" else null
        return isValid
    }
    fun validarTelefono(): Boolean {
        val isValid = _telefono.value.length == 8
        _telefonoError.value = if (!isValid) "El teléfono debe tener 8 dígitos" else null
        return isValid
    }
    fun validarCorreo(): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        val isValid = emailPattern.matcher(_correo.value).matches()
        _correoError.value = if (!isValid) "Correo electrónico no válido" else null
        return isValid
    }
    fun validarCorreoConfirmacion(): Boolean {
        val isValid = _correo.value == _recorreo.value
        _recorreoError.value = if (!isValid) "Los correos no coinciden" else null
        return isValid
    }
    fun validarContrasenaConfirmacion(): Boolean {
        val isValid = _contrasena.value == _recontrasena.value
        _recontrasenaError.value = if (!isValid) "Las contraseñas no coinciden" else null
        return isValid
    }
    fun validarFechaNacimiento(): Boolean {
        val datePattern = Regex("""\d{2}/\d{2}/\d{4}""")
        return datePattern.matches(_fechaNacimiento.value)
    }
    fun validarDocumentoUri(): Boolean {
        return _documentoUri.value.isNotEmpty()
    }

    fun validarFotoUri(): Boolean {
        return _fotoUri.value.isNotEmpty()
    }
    fun validarCUI(cui: String): Boolean {
        val PATTERN_CUI = """\d{13}"""
        val pattern = Regex(PATTERN_CUI)
        val validCui = pattern.matches(cui)

        if (!validCui) return false

        val no = cui.substring(0, 8)
        val depto = cui.substring(9, 11).toInt()
        val muni = cui.substring(11, 13).toInt()
        val ver = cui.substring(8, 9).toInt()

        val munisPorDepto = arrayOf(
            17, 8, 16, 16, 13, 14, 19, 8, 24, 21,
            9, 30, 32, 21, 8, 17, 14, 5, 11, 11, 7, 17
        )

        if (muni == 0 || depto == 0 || depto > munisPorDepto.size || muni > munisPorDepto[depto - 1]) {
            return false
        }

        var total = 0
        for (i in no.indices) {
            total += no[i].toString().toInt() * (i + 2)
        }

        return total % 11 == ver
    }
    fun validarContrasena(): Boolean {
        val isValid = _contrasena.value.length >= 6
        _contrasenaError.value = if (!isValid) "La contraseña debe tener al menos 6 caracteres" else null
        return isValid
    }
    fun validarReingresoContrasena(): Boolean {
        return _contrasena.value == _recontrasena.value
    }
    fun validarCodigoAcceso(): Boolean {
        val codigoAccesoPattern = Regex("^[0-9]+$")
        val isValid = _codigoAcceso.value.isNotEmpty() && codigoAccesoPattern.matches(_codigoAcceso.value)
        _codigoAccesoError.value = when {
            _codigoAcceso.value.isEmpty() -> "El código de acceso no puede estar vacío"
            !codigoAccesoPattern.matches(_codigoAcceso.value) -> "El código de acceso debe contener solo números"
            else -> null
        }
        return isValid
    }


    fun registrarPersona() {
        val currentPersona = _persona.value
        val currentCondominioId = _condominioId.value
        val currentSectorId = _sectorId.value

        if (currentPersona != null && currentCondominioId.isNotBlank() && currentSectorId.isNotBlank()) {
            if (!validarContrasena()) {
                _errorMessage.value = "La contraseña debe tener al menos 6 caracteres."
                return
            }
            if (!validarContrasenaConfirmacion()) {
                _errorMessage.value = "Las contraseñas no coinciden."
                return
            }
            if (!validarCorreo()) {
                _errorMessage.value = "El correo no tiene un formato válido."
                return
            }
            if (!validarCondominio()) {
                _errorMessage.value = "Debe seleccionar un condominio."
                return
            }
            if (!validarSector()) {
                _errorMessage.value = "Debe seleccionar un sector."
                return
            }
            if (!validarNombre()) {
                _errorMessage.value = "El nombre debe tener al menos 3 caracteres y comenzar con una letra mayúscula."
                return
            }
            if (!validarApellido()) {
                _errorMessage.value = "El apellido debe tener al menos 3 caracteres y comenzar con una letra mayúscula."
                return
            }
            if (!validarTelefono()) {
                _errorMessage.value = "El teléfono debe tener 7 dígitos."
                return
            }
            if (!validarCorreo()) {
                _errorMessage.value = "El correo no tiene un formato válido."
                return
            }
            if (_tipoDocumento.value == "DPI" && !validarCUI(_numeroDocumento.value)) {
                _errorMessage.value = "El CUI no es válido."
                return
            }
            if (!validarFechaNacimiento()) {
                _errorMessage.value = "La fecha de nacimiento debe estar en formato dd/MM/yyyy."
                return
            }
            if (!validarDocumentoUri()) {
                _errorMessage.value = "El documento URI no puede estar vacío."
                return
            }
            if (!validarFotoUri()) {
                _errorMessage.value = "El foto URI no puede estar vacío."
                return
            }

            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val response = registrarUseCase(currentCondominioId, currentSectorId, currentPersona)
                    if (response.estado) {
                        _persona.value = response.data
                        _errorMessage.value = null
                    } else {
                        _errorMessage.value = response.mensaje
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error de red: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Datos incompletos para el registro"
        }
    }

    fun onNombreChange(value: String) {
        _nombre.value = value
        validarNombre()
        updatePersona()

    }

    fun onApellidoChange(value: String) {
        _apellido.value = value
        validarApellido()
        updatePersona()
    }

    fun onCorreoChange(value: String) {
        _correo.value = value
        validarCorreo()
        updatePersona()
    }
    fun onReCorreoChange(value: String) {
        _recorreo.value = value
        validarCorreoConfirmacion()
        updatePersona()
    }

    fun onTelefonoChange(value: String) {
        _telefono.value = value
        validarTelefono()
        updatePersona()
    }

    fun onContrasenaChange(value: String) {
        _contrasena.value = value
        validarContrasena()
        updatePersona()
    }
    fun onReContrasenaChange(value: String) {
        _recontrasena.value = value
        validarContrasenaConfirmacion()
        updatePersona()
    }

    fun onNumeroDocumentoChange(value: String) {
        _numeroDocumento.value = value
        updatePersona()
    }

    fun onTipoDocumentoChange(value: String) {
        _tipoDocumento.value = value
        updatePersona()
    }

    fun onCodigoAccesoChange(value: String) {
        _codigoAcceso.value = value
        validarCodigoAcceso()
        updatePersona()
    }

    fun onFechaNacimientoChange(value: String) {
        _fechaNacimiento.value = value
        updatePersona()
    }

    fun onNumerocasaChange(value: String) {
        _numerocasa.value = value
        updatePersona()
    }

    fun onFotoUriChange(value: String) {
        _fotoUri.value = value
        updatePersona()
    }

    fun onDocumentoUriChange(value: String) {
        _documentoUri.value = value
        updatePersona()
    }

    fun onActivoChange(value: Boolean) {
        _activo.value = value
        updatePersona()
    }

    // Función para actualizar el objeto Persona
    private fun updatePersona() {
        _persona.value = Persona(
            id = _persona.value?.id,
            numeroCasa = _numerocasa.value.toIntOrNull() ?: 0,
            nombre = _nombre.value,
            apellido = _apellido.value,
            correo = _correo.value,
            telefono = _telefono.value,
            contrasena = _contrasena.value,
            numeroDocumento = _numeroDocumento.value,
            tipoDocumento = _tipoDocumento.value,
            codigoAcceso = _codigoAcceso.value,
            fechaNacimiento = _fechaNacimiento.value,
            activo = _activo.value,
            numerocasa = _numerocasa.value,
            fotoUri = _fotoUri.value,
            documentoUri = _documentoUri.value
        )
    }

}