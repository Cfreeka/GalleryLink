package com.ceph.gallerylink.presentation.fullimagescreen

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.domain.repository.ApiRepository
import com.ceph.gallerylink.domain.repository.Downloader
import com.ceph.gallerylink.presentation.navigation.Routes
import kotlinx.coroutines.launch

class FullImageViewModel(
    private val apiRepository: ApiRepository,
     private  val savedStateHandle: SavedStateHandle,
    private val downloader: Downloader
) : ViewModel() {

    private val imageId = savedStateHandle.toRoute<Routes.FullImageScreen>().imageId


    var image: UnsplashImage? by mutableStateOf(null)
        private set

    init {
        getImage()
    }
    private fun getImage() {
        viewModelScope.launch {
            try {
               val result = apiRepository.getImage(imageId)
                image = result
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

     fun downloadImage(url: String, fileName: String?) {
        viewModelScope.launch {
            try {
                downloader.downloadFile(url, fileName)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}








