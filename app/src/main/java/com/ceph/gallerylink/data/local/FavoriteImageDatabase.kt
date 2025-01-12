package com.ceph.gallerylink.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavouriteImageEntity::class,
        UnsplashImageEntity::class,
        UnsplashRemoteKeys::class
    ],
    version = 1
)
abstract class FavoriteImageDatabase : RoomDatabase() {
    abstract val dao: FavoriteImagesDao

    abstract val unsplashImageEntityDao: EditorialFeedDao
}