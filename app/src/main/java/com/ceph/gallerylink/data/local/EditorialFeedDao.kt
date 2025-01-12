package com.ceph.gallerylink.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface EditorialFeedDao {

    @Query("SELECT * FROM unsplash_images_entity")
    fun getAllEditorialImages(): PagingSource<Int, UnsplashImageEntity>


    @Upsert
    suspend fun upsertEditorialImages(images: List<UnsplashImageEntity>)


    @Query("DELETE FROM unsplash_images_entity")
    suspend fun deleteAllEditorialImages()



    @Query("SELECT * FROM unsplash_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Upsert
    suspend fun insertAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM unsplash_remote_keys")
    suspend fun deleteAllRemoteKeys()
}