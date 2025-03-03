package com.ceph.gallerylink.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(
    val route: String
) {

    @Serializable
    data object HomeScreen: Routes(
        route = "home"
    )
    @Serializable
    data object FavoritesScreen: Routes(
        "favorite"
    )
    @Serializable
    data object SearchScreen: Routes(
        "search"
    )
    @Serializable
    data class FullImageScreen (val imageId: String): Routes(
        "fullImage"
    )
    @Serializable
    data class ProfilesScreen(val profileLInk: String): Routes(
        "profile"
    )

}