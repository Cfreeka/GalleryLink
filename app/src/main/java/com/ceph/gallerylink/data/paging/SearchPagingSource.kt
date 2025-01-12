package com.ceph.gallerylink.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ceph.gallerylink.data.mappers.toDomainImageList
import com.ceph.gallerylink.data.remote.api.ApiService
import com.ceph.gallerylink.domain.model.UnsplashImage

class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {


    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        return try {

            val currentPage = params.key ?: STARTING_PAGE_INDEX

            val response = apiService.searchImages(
                query = query,
                page = currentPage,
                perPage = params.loadSize

            )
            val endPaginationReached = response.images.isEmpty()

            LoadResult.Page(
                data = response.images.toDomainImageList(),
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (endPaginationReached) null else currentPage + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }
}