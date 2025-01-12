package com.ceph.gallerylink.ui

import android.app.Application
import com.ceph.gallerylink.di.databaseModule
import com.ceph.gallerylink.di.viewModelModules
import com.ceph.gallerylink.di.repositoryModules
//import com.ceph.imagevista.di.connectivityModule
import com.ceph.gallerylink.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ImageVistaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ImageVistaApp)
            modules(networkModule, viewModelModules, repositoryModules, databaseModule)
        }
    }
}