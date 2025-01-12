package com.ceph.gallerylink.presentation.fullimagescreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.presentation.components.CustomLoadingBar
import com.ceph.gallerylink.presentation.components.DownloadDrawerSheet
import com.ceph.gallerylink.presentation.components.FullImageTopBar
import com.ceph.gallerylink.presentation.components.ImageDownloadOption
import com.ceph.gallerylink.presentation.utils.rememberWindowInsetsControllerCompat
import com.ceph.gallerylink.presentation.utils.toToggleSystemBar
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullImageScreen(
    image: UnsplashImage?,
    onBackClick: () -> Unit,
    onPhotographerNameClick: (String) -> Unit,
    onImageDownloadClick: (String, String?) -> Unit
) {

    val context = LocalContext.current
    val windowInsetsController = rememberWindowInsetsControllerCompat()
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showBars by rememberSaveable { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDownLoadSheetOpen by remember { mutableStateOf(false) }

    DownloadDrawerSheet(
        isOpen = isDownLoadSheetOpen,
        sheetState = sheetState,
        onDismissRequest = { /*TODO*/ },
        onOptionClick ={ option ->

            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isDownLoadSheetOpen = false
            }

            val url = when(option){
                ImageDownloadOption.SMALL -> image?.imageUrlSmall
                ImageDownloadOption.MEDIUM -> image?.imageUrlRegular
                ImageDownloadOption.ORIGINAL -> image?.imageUrlRaw
            }

            url?.let { onImageDownloadClick(it, option.name) }

            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()

        }
    )



    LaunchedEffect(key1 = Unit) {
        windowInsetsController.toToggleSystemBar(showBars)
    }

    BackHandler {
        windowInsetsController.toToggleSystemBar(true)
        onBackClick()
    }

    val imageLoader = rememberAsyncImagePainter(
        model = image?.imageUrlRaw,
        onState = { imageState ->
            isLoading = imageState is AsyncImagePainter.State.Loading
            isError = imageState is AsyncImagePainter.State.Error

        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val isImageZoomed: Boolean by remember {
                derivedStateOf { scale != 1f }
            }
            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->

                scale = max(scale * zoomChange, 1f)
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                val maxY = (constraints.maxHeight * (scale - 1)) / 2

                offset = Offset(
                    x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                    y = offset.y + offsetChange.y.coerceIn(-maxY, maxY)
                )

            }

            if (isLoading) {
                CustomLoadingBar()
            }
            Image(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .transformable(state = transformState)
                    .combinedClickable(
                        onDoubleClick = {
                            if (isImageZoomed) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scope.launch {
                                    transformState.animateZoomBy(3f)
                                }

                            }
                        },
                        onClick = {
                            showBars = !showBars
                            windowInsetsController.toToggleSystemBar(showBars)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    },
                painter = imageLoader,
                contentDescription = null,
            )
        }



        FullImageTopBar(
            modifier = Modifier
                .align(TopCenter)
                .padding(start = 10.dp, top = 50.dp)
                .fillMaxWidth(),
            image = image,
            isVisible = showBars,
            onPhotographerNameClick = onPhotographerNameClick,
            onBackClick = onBackClick,
            onDownloadImageClick = {isDownLoadSheetOpen = true}
        )
    }

}