package com.ceph.gallerylink.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ceph.gallerylink.domain.model.UnsplashImage

@Composable
fun ImageVerticalGrid(
    modifier: Modifier = Modifier,
    favouriteImageIds: List<String>,
    images: LazyPagingItems<UnsplashImage>,
    onImageClick: (String) -> Unit,
    onImageDragStart: (UnsplashImage?) -> Unit,
    onImageDragEnd: () -> Unit,
    onToggleStatus: (UnsplashImage) -> Unit,
) {


    LazyVerticalStaggeredGrid(

        columns = StaggeredGridCells.Adaptive(120.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
        verticalItemSpacing = 10.dp,
        contentPadding = PaddingValues(10.dp)

    ) {
        items(count =  images.itemCount) { index ->

            val image = images[index]
            ImageCard(
                image = image,
                modifier = Modifier
                    .clickable { image?.id?.let { onImageClick(it) } }
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress (
                            onDragStart = {onImageDragStart(image)},
                            onDragEnd = {onImageDragEnd()},
                            onDragCancel = {onImageDragEnd()},
                            onDrag = {_,_ ->}

                        )
                    },
                isFavorite = favouriteImageIds.contains(
                    image?.id
                ) ,
                onFavoriteClick =  {image?.let { onToggleStatus(it) }}
            )
        }



    }
}