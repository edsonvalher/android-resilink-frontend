package com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
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

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private val _requestPermissions = MutableStateFlow(false)
    val requestPermissions: StateFlow<Boolean> = _requestPermissions.asStateFlow()

    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted.asStateFlow()

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
            // No solicites permisos directamente aquí; notifica que se necesitan permisos
            _requestPermissions.value = true
        }
    }

    fun checkPermissions() {
        _requestPermissions.value = true
    }

    fun onPermissionsResult(granted: Boolean) {
        _permissionsGranted.value = granted
        _requestPermissions.value = false
    }

    fun onAddPhotoClicked(activity: Activity, uri: Uri) {
        if (_permissionsGranted.value) {
            viewModelScope.launch {
                selectImageFromGalleryUseCase(activity)
                _imageUri.value = uri
            }
        } else {
            checkAndRequestPermissions(activity)
        }
    }

    fun onTakePhotoClicked(activity: Activity, uri: Uri) {
        if (_permissionsGranted.value) {
            viewModelScope.launch {
                takePhotoWithCameraUseCase(activity)
                _imageUri.value = uri
            }
        } else {
            checkAndRequestPermissions(activity)
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1001
    }
}
