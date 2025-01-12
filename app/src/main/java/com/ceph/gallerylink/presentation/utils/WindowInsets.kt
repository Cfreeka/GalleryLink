package com.ceph.gallerylink.presentation.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun rememberWindowInsetsControllerCompat(): WindowInsetsControllerCompat {

    val window = with(LocalContext.current as Activity) { return@with window }

    return remember {
        WindowInsetsControllerCompat(window, window.decorView)
    }
}


fun WindowInsetsControllerCompat.toToggleSystemBar(show: Boolean) {
    if (show) show(WindowInsetsCompat.Type.systemBars())
    else hide(WindowInsetsCompat.Type.systemBars())
}