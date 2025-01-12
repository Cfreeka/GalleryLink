package com.ceph.gallerylink.presentation.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.domain.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val _searchImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())

    val searchedImages = _searchImages.asStateFlow()

   val imageIds : StateFlow<List<String>> = apiRepository.getFavouriteImageIds()
       .stateIn(
           viewModelScope,
           SharingStarted.WhileSubscribed(5000),
           emptyList()
       )


    fun getSearchImages(query: String) {
        viewModelScope.launch {
            apiRepository
                .searchImages(query)
                .cachedIn(viewModelScope)
                .collect { _searchImages.value = it }
        }
    }

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