package com.ceph.gallerylink.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("unsplash_remote_keys")
data class UnsplashRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
