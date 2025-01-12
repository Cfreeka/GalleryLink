package com.ceph.gallerylink.data.remote.api

import com.ceph.gallerylink.data.remote.dto.UnsplashImageDto
import com.ceph.gallerylink.data.remote.dto.UnsplashImagesSearchResult
import com.ceph.gallerylink.util.Constants.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("/photos")
    suspend fun getEditorialFeedImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImageDto>

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId: String
    ): UnsplashImageDto

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashImagesSearchResult



}