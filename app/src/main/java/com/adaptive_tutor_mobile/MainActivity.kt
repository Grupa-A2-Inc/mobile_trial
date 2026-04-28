package com.adaptive_tutor_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adaptive_tutor_mobile.di.SessionStore
import com.adaptive_tutor_mobile.presentation.navigation.AppNavGraph
import com.adaptive_tutor_mobile.presentation.navigation.Screen
import com.adaptive_tutor_mobile.presentation.navigation.routeForRole
import com.adaptive_tutor_mobile.ui.theme.Adaptive_tutor_mobileTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionStore: SessionStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Determine start destination before composition to avoid runBlocking inside @Composable
        val startDestination = runBlocking {
            val user = sessionStore.getUser()
            if (user != null) routeForRole(user.role) else Screen.Login.route
        }

        setContent {
            Adaptive_tutor_mobileTheme {
                AppNavGraph(
                    startDestination = startDestination,
                    sessionStore = sessionStore
                )
            }
        }
    }
}