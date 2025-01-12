package com.ceph.gallerylink.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun NetworkStatusBar(
    modifier: Modifier = Modifier,
    showMessageBar: Boolean,
    backgroundColor: Color,
    message: String
) {


    AnimatedVisibility(
        visible = showMessageBar,
        enter = slideInVertically { height -> height },
        exit =  slideOutVertically { height -> height }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = message, style = MaterialTheme.typography.bodySmall, color = Color.White)
        }

    }

}