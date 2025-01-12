package com.ceph.gallerylink.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteImagesDao {

    @Upsert
    suspend fun insertFavoriteImage(image: FavouriteImageEntity)

    @Delete
    suspend fun deleteFavoriteImage(image: FavouriteImageEntity)

    @Query("SELECT * FROM favorite_images")
    fun getAllFavoriteImages(): PagingSource<Int, FavouriteImageEntity>

    @Query(" SELECT EXISTS (SELECT 1 FROM favorite_images WHERE id = :imageId)")
    suspend fun isImageFavourite(imageId: String): Boolean

    @Query("SELECT id FROM favorite_images")
    fun getImageIds(): Flow<List<String>>

}

