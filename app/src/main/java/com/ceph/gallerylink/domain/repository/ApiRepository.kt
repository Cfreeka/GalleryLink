package com.ceph.gallerylink.domain.repository

import androidx.paging.PagingData
import com.ceph.gallerylink.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

     fun getEditorialImages(): Flow<PagingData<UnsplashImage>>

    suspend fun getImage(imageId: String): UnsplashImage


    suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>>


    suspend fun toggleFavoriteStatus(image: UnsplashImage)

     fun getFavouriteImageIds(): Flow<List<String>>

     fun getAllFavouriteImages(): Flow<PagingData<UnsplashImage>>
}