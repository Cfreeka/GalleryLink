package com.ceph.gallerylink.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ceph.gallerylink.R
import com.ceph.gallerylink.domain.model.UnsplashImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageVistaTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Gallery Link",
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit = {},
    navigation: @Composable () -> Unit = {}

) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                modifier = modifier,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(title.split(" ").first())
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                        append(" ${title.split(" ").last()}")
                    }
                },
                fontWeight = FontWeight.ExtraBold
            )
        },
        actions = {

            IconButton(onClick = { onSearchClick() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }

        },
        navigationIcon = navigation
    )
}

@Composable
fun FullImageTopBar(
    modifier: Modifier = Modifier,
    image: UnsplashImage?,
    isVisible: Boolean,
    onBackClick: () -> Unit,
    onPhotographerNameClick: (String) -> Unit,
    onDownloadImageClick: () -> Unit

) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {


        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            AsyncImage(
                model = image?.photographerProfileImgUrl,
                modifier = Modifier
                    .padding(10.dp)
                    .size(30.dp)
                    .clip(CircleShape),
                contentDescription = "Profile image"
            )

            Column(
                modifier = Modifier.clickable {
                    image?.let { onPhotographerNameClick(it.photographerProfileLink) }
                }
            ) {

                Text(
                    text = image?.photographerName ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = image?.photographerUsername ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            IconButton(onClick = { onDownloadImageClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = null
                )

            }


        }
    }
}














