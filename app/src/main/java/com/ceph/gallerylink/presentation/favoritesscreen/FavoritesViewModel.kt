package com.ceph.gallerylink.presentation.favoritesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.domain.repository.ApiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val apiRepository: ApiRepository) : ViewModel() {


    val favouriteImages: StateFlow<PagingData<UnsplashImage>> =
        apiRepository.getAllFavouriteImages()
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PagingData.empty()
            )

    val favouriteImageIds: StateFlow<List<String>> = apiRepository.getFavouriteImageIds()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun toggleFavouriteStatus(image: UnsplashImage) {
        viewModelScope.launch {
            try {
                apiRepository.toggleFavoriteStatus(image)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
