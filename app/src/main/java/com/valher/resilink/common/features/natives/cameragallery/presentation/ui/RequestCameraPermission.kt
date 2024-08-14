package com.valher.resilink.common.features.natives.cameragallery.presentation.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel.CameraGalleryViewModel

@Composable
fun RequestCameraPermission(
    viewModel: CameraGalleryViewModel = hiltViewModel(),
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: @Composable () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    var permissionState by remember { mutableStateOf<Boolean?>(null) }
    val currentOnPermissionGranted by rememberUpdatedState(onPermissionGranted)
    val currentOnPermissionDenied by rememberUpdatedState(onPermissionDenied)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.onPermissionsResult(isGranted)
        permissionState = isGranted
    }

    LaunchedEffect(Unit) {
        if (!viewModel.permissionsGranted.value) {
            launcher.launch(android.Manifest.permission.CAMERA)
        } else {
            permissionState = true
        }
    }

    when (permissionState) {
        true -> currentOnPermissionGranted()
        false -> currentOnPermissionDenied()
        null -> {} // No mostrar nada mientras la solicitud de permiso está en progreso
    }
}
