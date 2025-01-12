package com.ceph.gallerylink.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.ceph.gallerylink.presentation.homescreen.HomeViewModel
import com.ceph.gallerylink.presentation.favoritesscreen.FavoritesScreen
import com.ceph.gallerylink.presentation.favoritesscreen.FavoritesViewModel
import com.ceph.gallerylink.presentation.fullimagescreen.FullImageScreen
import com.ceph.gallerylink.presentation.fullimagescreen.FullImageViewModel
import com.ceph.gallerylink.presentation.homescreen.HomeScreen
import com.ceph.gallerylink.presentation.profilescreen.ProfilesScreen
import com.ceph.gallerylink.presentation.searchscreen.SearchScreen
import com.ceph.gallerylink.presentation.searchscreen.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostSetUp(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {


    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {

            val homeViewModel = koinViewModel<HomeViewModel>()

            val images = homeViewModel.editorialImages.collectAsLazyPagingItems()
            val imageIds by homeViewModel.favouriteImageIds.collectAsStateWithLifecycle()

            HomeScreen(
                images = images,
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                scrollBehavior = scrollBehavior,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))

                },
                onFABCLick = {
                    navController.navigate(Routes.FavoritesScreen)
                },
                onToggleFavoriteStatus = { image ->
                    homeViewModel.toggleFavouriteStatus(image)
                },
                favouriteImageIds = imageIds
            )
        }
        composable<Routes.SearchScreen> {


            val searchViewModel = koinViewModel<SearchViewModel>()

            val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()

            val imageIds by searchViewModel.imageIds.collectAsStateWithLifecycle()

            SearchScreen(
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                searchedImages = searchedImages,
                onSearch = { query ->

                    searchViewModel.getSearchImages(query)
                },
                onSearchQueryChange = onSearchQueryChange,
                searchQuery = searchQuery,
                onToggleStatus = { image ->
                    searchViewModel.toggleFavouriteStatus(image)
                },
                favouriteImageIds = imageIds
            )
        }

        composable<Routes.FavoritesScreen> {

            val favoritesViewModel = koinViewModel<FavoritesViewModel>()
            val favouriteImages = favoritesViewModel.favouriteImages.collectAsLazyPagingItems()
            val favouriteImageIds by favoritesViewModel.favouriteImageIds.collectAsStateWithLifecycle()
            FavoritesScreen(
                onBackClick = { navController.navigateUp() },
                favouriteImageIds = favouriteImageIds,
                favouriteImages = favouriteImages,
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                scrollBehavior = scrollBehavior,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onToggleFavoriteStatus = { image ->
                    favoritesViewModel.toggleFavouriteStatus(image)
                }

            )
        }

        composable<Routes.ProfilesScreen> { backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfilesScreen>().profileLInk

            ProfilesScreen(
                profileLink = profileLink,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable<Routes.FullImageScreen> {

            val fullImageViewModel = koinViewModel<FullImageViewModel>()
            FullImageScreen(

                image = fullImageViewModel.image,
                onBackClick = {
                    navController.navigateUp()
                },
                onPhotographerNameClick = { profileLink ->
                    navController.navigate(Routes.ProfilesScreen(profileLink))
                },
                onImageDownloadClick = { url, fileName ->
                    fullImageViewModel.downloadImage(url, fileName)
                }
            )


        }
    }

}