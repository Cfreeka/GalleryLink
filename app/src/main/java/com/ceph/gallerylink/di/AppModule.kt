package com.ceph.gallerylink.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.ceph.gallerylink.data.local.FavoriteImageDatabase
import com.ceph.gallerylink.data.remote.api.ApiService
import com.ceph.gallerylink.data.repository.AndroidFileDownloader
import com.ceph.gallerylink.data.repository.ApiRepositoryImpl
import com.ceph.gallerylink.data.repository.NetworkConnectivityObserverImpl
//import com.ceph.imagevista.data.repository.NetworkConnectivityObserverImpl
import com.ceph.gallerylink.domain.repository.ApiRepository
import com.ceph.gallerylink.domain.repository.Downloader
import com.ceph.gallerylink.domain.repository.NetworkConnectivityObserver
import com.ceph.gallerylink.presentation.favoritesscreen.FavoritesViewModel
//import com.ceph.imagevista.domain.repository.NetworkConnectivityObserver
import com.ceph.gallerylink.presentation.fullimagescreen.FullImageViewModel
import com.ceph.gallerylink.presentation.homescreen.HomeViewModel
import com.ceph.gallerylink.presentation.searchscreen.SearchViewModel
import com.ceph.gallerylink.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {


    single {

        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(Constants.BASE_URL)
            .build()

    }

    single { get<Retrofit>().create(ApiService::class.java) }


}
val viewModelModules = module {

    viewModel { HomeViewModel(get()) }

    viewModel { (savedStateHandle: SavedStateHandle) ->
        FullImageViewModel(
            apiRepository = get(),
            savedStateHandle = savedStateHandle,
            downloader = get()
        )

    }
    viewModel { SearchViewModel(get()) }

    viewModel { FavoritesViewModel(get()) }


}


val repositoryModules = module {

    single { AndroidFileDownloader(get()) } bind Downloader::class

    single { ApiRepositoryImpl(get(), get()) } bind ApiRepository::class



    factory<NetworkConnectivityObserver> { (context: Context, scope: CoroutineScope) ->
        NetworkConnectivityObserverImpl(context, scope)

    }

}

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteImageDatabase::class.java,
            "Favorite_db"
        ).build()
    }


    single { get<FavoriteImageDatabase>().dao }

}















