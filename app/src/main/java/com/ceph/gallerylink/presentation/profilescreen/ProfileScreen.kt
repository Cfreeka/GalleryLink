package com.ceph.gallerylink.presentation.profilescreen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.ceph.gallerylink.presentation.components.CustomLoadingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilesScreen(
    profileLink: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text(text = "Profile") },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        )


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


            AndroidView(
                factory = {
                    WebView(context).apply {
                        object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }
                        }
                        loadUrl(profileLink)
                        isLoading = false
                    }
                }

            )
            if (isLoading) {
                CustomLoadingBar()
            }

        }

    }


}