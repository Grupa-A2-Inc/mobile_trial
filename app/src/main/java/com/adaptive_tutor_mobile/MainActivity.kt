package com.adaptive_tutor_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.adaptive_tutor_mobile.di.SessionStore
import com.adaptive_tutor_mobile.presentation.navigation.AppNavGraph
import com.adaptive_tutor_mobile.presentation.navigation.Screen
import com.adaptive_tutor_mobile.presentation.navigation.routeForRole
import com.adaptive_tutor_mobile.ui.theme.Adaptive_tutor_mobileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionStore: SessionStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Adaptive_tutor_mobileTheme {
                var startDestination by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    val user = sessionStore.getUser()
                    startDestination = if (user != null) routeForRole(user.role) else Screen.Login.route
                }

                if (startDestination != null) {
                    AppNavGraph(
                        startDestination = startDestination!!,
                        sessionStore = sessionStore
                    )
                }
            }
        }
    }
}