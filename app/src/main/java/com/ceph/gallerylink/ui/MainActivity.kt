package com.ceph.gallerylink.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ceph.gallerylink.domain.model.NetworkStatus
import com.ceph.gallerylink.domain.repository.NetworkConnectivityObserver
import com.ceph.gallerylink.presentation.components.NetworkStatusBar
import com.ceph.gallerylink.presentation.navigation.NavHostSetUp
import com.ceph.gallerylink.ui.theme.ImageVistaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val repo: NetworkConnectivityObserver
            by inject { parametersOf(this@MainActivity, coroutineScope) }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()


        enableEdgeToEdge()
        setContent {
            val status by repo.networkStatus.collectAsState()
            var showMessageBar by rememberSaveable {
                mutableStateOf(false)
            }

            var backgroundColor by remember {
                mutableStateOf(Color.Red)
            }
            var message by rememberSaveable {
                mutableStateOf("")
            }

            LaunchedEffect(key1 = status) {
                when (status) {
                    NetworkStatus.Connected -> {
                        message = "Back Online"
                        backgroundColor = Color.Green
                        delay(3000)
                        showMessageBar = false

                    }

                    NetworkStatus.Disconnected -> {
                        showMessageBar = true
                        message = "No internet connection"
                        backgroundColor = Color.Red
                    }
                }
            }
            ImageVistaTheme {


                val navController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                var searchQuery by rememberSaveable {
                    mutableStateOf("")
                }



                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    bottomBar = {
                        NetworkStatusBar(
                            showMessageBar = showMessageBar,
                            backgroundColor = backgroundColor,
                            message = message
                        )
                    }

                ) {
                    NavHostSetUp(
                        navController = navController,
                        scrollBehavior = scrollBehavior,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                    )

                }

            }
        }
    }
}
