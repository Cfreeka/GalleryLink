package com.ceph.gallerylink.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ceph.gallerylink.R
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.presentation.components.ImageVerticalGrid
import com.ceph.gallerylink.presentation.components.ImageVistaTopAppBar
import com.ceph.gallerylink.presentation.components.ZoomedImageCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    images: LazyPagingItems<UnsplashImage>,
    onSearchClick: () -> Unit,
    favouriteImageIds: List<String>,
    onFABCLick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onImageClick: (String) -> Unit,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            ImageVistaTopAppBar(onSearchClick = onSearchClick, scrollBehavior = scrollBehavior)

            ImageVerticalGrid(
                images = images,
                onImageClick = onImageClick,
                onImageDragStart = { image ->
                    activeImage = image
                    showImagePreview = true

                },
                onImageDragEnd = { showImagePreview = false },
                onToggleStatus = onToggleFavoriteStatus,
                favouriteImageIds = favouriteImageIds
            )
        }

        FloatingActionButton(
            onClick = { onFABCLick() },
            modifier = Modifier
                .padding(end = 24.dp, bottom = 55.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Favorites",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        ZoomedImageCard(image = activeImage, isVisible = showImagePreview)

    }

}