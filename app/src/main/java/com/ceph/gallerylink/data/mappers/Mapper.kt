package com.ceph.gallerylink.data.mappers

import com.ceph.gallerylink.data.local.FavouriteImageEntity
import com.ceph.gallerylink.data.local.UnsplashImageEntity
import com.ceph.gallerylink.data.remote.dto.UnsplashImageDto
import com.ceph.gallerylink.domain.model.UnsplashImage

fun UnsplashImageDto.toDomainModel(): UnsplashImage {

    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImgUrl = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = description
    )


}


fun UnsplashImage.toFavouriteImageEntity(): FavouriteImageEntity {
    return FavouriteImageEntity(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = description ?: ""
    )
}

fun FavouriteImageEntity.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = description ?: ""
    )
}

fun UnsplashImageDto.toEntity(): UnsplashImageEntity {
    return UnsplashImageEntity(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImgUrl = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = description ?: ""
    )
}

fun UnsplashImageEntity.toUnsplashImage(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = description ?: ""

    )
}


fun List<UnsplashImageDto>.toDomainImageList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}

fun List<UnsplashImageDto>.toEntityImageList(): List<UnsplashImageEntity> {
    return this.map { it.toEntity() }
}
