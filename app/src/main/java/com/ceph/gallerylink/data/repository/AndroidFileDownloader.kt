package com.ceph.gallerylink.data.repository

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.ceph.gallerylink.domain.repository.Downloader
import java.io.File

class AndroidFileDownloader(
    context: Context
) : Downloader {


    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun downloadFile(url: String, fileName: String?) {
        try {

            val title = fileName ?: "New Image"

            val request = DownloadManager.Request(url.toUri())
                .setTitle(title)
                .setMimeType("image/*")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + title + ".jpg"

                )

            downloadManager.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}