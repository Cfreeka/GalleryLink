package com.ceph.gallerylink.domain.repository

import com.ceph.gallerylink.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {

    val networkStatus: StateFlow<NetworkStatus>
}
