package com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.resilink.common.features.natives.cameragallery.domain.usecases.SelectImageFromGalleryUseCase
import com.valher.resilink.common.features.natives.cameragallery.domain.usecases.TakePhotoWithCameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraGalleryViewModel @Inject constructor(
    private val selectImageFromGalleryUseCase: SelectImageFromGalleryUseCase,
    private val takePhotoWithCameraUseCase: TakePhotoWithCameraUseCase
) : ViewModel() {

    var TAG = "CameraGalleryViewModel"

    // Estado para la URI de la imagen seleccionada
    private val _imageUriCamera = MutableStateFlow<Uri?>(null)
    val imageUriCamera: StateFlow<Uri?> = _imageUriCamera.asStateFlow()

    private val _imageUriGallery = MutableStateFlow<Uri?>(null)
    val imageUriGallery: StateFlow<Uri?> = _imageUriGallery.asStateFlow()

    // Estado para gestionar la solicitud de permisos
    private val _requestPermissions = MutableStateFlow(false)
    val requestPermissions: StateFlow<Boolean> = _requestPermissions.asStateFlow()

    // Estado para indicar si los permisos fueron concedidos
    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted.asStateFlow()



    // Función para verificar y solicitar permisos si es necesario
    fun checkAndRequestPermissions(activity: Activity) {
        val cameraPermission = ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.CAMERA
        )
        val storagePermission = ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
            storagePermission == PackageManager.PERMISSION_GRANTED
        ) {
            _permissionsGranted.value = true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_PERMISSIONS_CODE
            )
            _requestPermissions.value = true
        }
    }

    // Función para manejar el resultado de la solicitud de permisos
    fun onPermissionsResult(granted: Boolean) {
        _permissionsGranted.value = granted
        _requestPermissions.value = false
    }

    // Función para seleccionar una imagen desde la galería
    fun onAddPhotoClicked(activity: Activity) {
        if (_permissionsGranted.value) {
            viewModelScope.launch {
                selectImageFromGalleryUseCase(activity)
                // La URI se establece en MainActivity después de seleccionar la imagen
            }
        } else {
            checkAndRequestPermissions(activity)
        }
    }

    // Función para tomar una foto con la cámara
    fun onTakePhotoClicked(activity: Activity, uri: Uri) {
        if (_permissionsGranted.value) {
            viewModelScope.launch {
                takePhotoWithCameraUseCase(activity)
                _imageUriCamera.value = uri
            }
        } else {
            checkAndRequestPermissions(activity)
        }
    }

    // Función para establecer la URI de la imagen desde MainActivity
    fun setImageUriCamera(uri: Uri) {
        _imageUriCamera.value = uri
        Log.d(TAG, "setImageUri: $uri")
    }
    fun setImageUriGallery(uri: Uri) {
        _imageUriGallery.value = uri
        Log.d(TAG, "setImageUri: $uri")
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1001
    }
}