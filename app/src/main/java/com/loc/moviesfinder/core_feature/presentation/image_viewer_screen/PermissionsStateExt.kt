package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied(): Boolean {
    return !shouldShowRationale && !hasPermission
}