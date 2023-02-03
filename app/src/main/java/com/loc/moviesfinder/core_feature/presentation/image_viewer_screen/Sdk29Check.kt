package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import android.os.Build

 fun sdk29AndUp(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}