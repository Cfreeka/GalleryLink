package com.ceph.gallerylink.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ceph.gallerylink.data.local.FavoriteImageDatabase
import com.ceph.gallerylink.data.local.UnsplashImageEntity
import com.ceph.gallerylink.data.local.UnsplashRemoteKeys
import com.ceph.gallerylink.data.mappers.toEntityImageList
import com.ceph.gallerylink.data.remote.api.ApiService

@OptIn(ExperimentalPagingApi::class)
class EditorialMediator(
    private val api: ApiService,
    private val unsplashDatabase: FavoriteImageDatabase
) : RemoteMediator<Int, UnsplashImageEntity>() {

    private val editorialFeedDao = unsplashDatabase.unsplashImageEntityDao

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageEntity>
    ): MediatorResult {
        try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForTheFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForTheLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    nextPage
                }
            }

            val response = api.getEditorialFeedImages(
                page = currentPage,
                perPage = 10
            )
            val endOfPaginationReached = response.isEmpty()
            val prevPage = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            unsplashDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    editorialFeedDao.deleteAllEditorialImages()
                    editorialFeedDao.deleteAllRemoteKeys()
                }
                val remoteKeys = response.map { unsplashIMageDto ->
                    UnsplashRemoteKeys(
                        id = unsplashIMageDto.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )

                }

                editorialFeedDao.insertAllRemoteKeys(remoteKeys)
                editorialFeedDao.upsertEditorialImages(response.toEntityImageList())


            }
            return MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }


    }

    private suspend fun getRemoteKeyForTheFirstItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                editorialFeedDao.getRemoteKeys(id = unsplashImage.id)
            }


    }

    private suspend fun getRemoteKeyForTheLastItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                editorialFeedDao.getRemoteKeys(id = unsplashImage.id)
            }


    }

}

















