package com.ceph.gallerylink.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ceph.gallerylink.data.local.FavoriteImageDatabase
import com.ceph.gallerylink.data.mappers.toDomainModel
import com.ceph.gallerylink.data.mappers.toFavouriteImageEntity
import com.ceph.gallerylink.data.mappers.toUnsplashImage
import com.ceph.gallerylink.data.paging.EditorialMediator
import com.ceph.gallerylink.data.paging.SearchPagingSource
import com.ceph.gallerylink.data.remote.api.ApiService
import com.ceph.gallerylink.domain.model.UnsplashImage
import com.ceph.gallerylink.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ApiRepositoryImpl(
    private val apiService: ApiService,
    private val database: FavoriteImageDatabase
) : ApiRepository {


    private val dao = database.dao
    private val editorialDao = database.unsplashImageEntityDao
    @OptIn(ExperimentalPagingApi::class)
    override  fun getEditorialImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = EditorialMediator(apiService, database),
            pagingSourceFactory = {
                editorialDao.getAllEditorialImages()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toUnsplashImage() }
            }
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return apiService.getImage(imageId).toDomainModel()
    }

    override suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(apiService = apiService, query = query)
            }
        ).flow
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {

        val isFavorite = dao.isImageFavourite(image.id)
        val favouriteImageEntity = image.toFavouriteImageEntity()


        if (isFavorite) {
            dao.deleteFavoriteImage(favouriteImageEntity)
        } else {
            dao.insertFavoriteImage(favouriteImageEntity)
        }
    }

    override fun getFavouriteImageIds(): Flow<List<String>> {
        return dao.getImageIds()
    }

    override fun getAllFavouriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                dao.getAllFavoriteImages()
            }
        )
            .flow
            .map { pagingData ->

                pagingData.map { it.toDomainModel() }
            }
    }
}