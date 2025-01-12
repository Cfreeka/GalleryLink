package com.ceph.gallerylink.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object HomeScreen: Routes()
    @Serializable
    data object FavoritesScreen: Routes()
    @Serializable
    data object SearchScreen: Routes()
    @Serializable
    data class FullImageScreen (val imageId: String): Routes()
    @Serializable
    data class ProfilesScreen(val profileLInk: String): Routes()

}