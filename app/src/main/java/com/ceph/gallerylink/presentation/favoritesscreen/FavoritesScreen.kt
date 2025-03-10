package com.ceph.gallerylink.presentation.favoritesscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.presentation.components.ImageVerticalGrid
import com.ceph.gallerylink.presentation.components.ZoomedImageCard


@Composable
fun FavoritesScreen(
    favouriteImageIds: List<String>,
    favouriteImages: LazyPagingItems<UnsplashImage>,
    onImageClick: (String) -> Unit,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
    paddingValues: PaddingValues

) {

    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            ImageVerticalGrid(
                images = favouriteImages,
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


        ZoomedImageCard(image = activeImage, isVisible = showImagePreview)

        if (favouriteImages.itemCount == 0) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }

    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "No Saved Images",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Images you save will be stored here",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}