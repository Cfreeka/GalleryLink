package com.ceph.gallerylink.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "unsplash_images_entity")
data class UnsplashImageEntity(
    @PrimaryKey
    val id: String,
    val imageUrlSmall: String,
    val imageUrlRegular: String,
    val imageUrlRaw: String,
    val photographerName: String,
    val photographerUsername: String,
    val photographerProfileImgUrl: String,
    val photographerProfileLink: String,
    val width: Int,
    val height: Int,
    val description: String
)



