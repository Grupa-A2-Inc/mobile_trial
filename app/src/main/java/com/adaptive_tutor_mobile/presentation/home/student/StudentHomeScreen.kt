package com.adaptive_tutor_mobile.presentation.home.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.adaptive_tutor_mobile.presentation.auth.AuthViewModel
import kotlinx.coroutines.launch

private data class ActionCard(val title: String, val subtitle: String, val icon: ImageVector)

private val studentCards = listOf(
    ActionCard("Cursurile mele",    "GET /api/v1/students/me/courses",          Icons.Filled.MenuBook),
    ActionCard("Explorează",        "GET /api/v1/courses/public",               Icons.Filled.Explore),
    ActionCard("Sesiune Adaptive",  "POST /api/v1/adaptive/start",              Icons.Filled.Psychology),
    ActionCard("Certificate",       "GET /api/v1/students/me/completed-courses",Icons.Filled.EmojiEvents)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val currentUser by viewModel.currentUser.collectAsState()
    val firstName = currentUser?.firstName ?: "Student"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bună, $firstName") },
                actions = {
                    IconButton(onClick = { viewModel.logout(); onLogout() }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Deconectare")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(studentCards) { card ->
                ElevatedCard(
                    onClick = { scope.launch { snackbarHostState.showSnackbar("Coming soon") } }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(card.icon, contentDescription = card.title)
                        Text(card.title, style = MaterialTheme.typography.titleSmall)
                        Text(card.subtitle, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
