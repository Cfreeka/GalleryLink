package com.ceph.gallerylink.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun CustomLoadingBar(
    modifier: Modifier = Modifier
) {

    val animation = rememberInfiniteTransition(label = "Loading bar")
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )
    Box(
        modifier = modifier
            .size(80.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                width = 8.dp,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.onBackground
            )
    )
}