package com.ceph.gallerylink.domain.repository

interface Downloader {

    fun downloadFile(url: String, fileName: String?)
}
