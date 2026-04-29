package com.adaptive_tutor_mobile.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adaptive_tutor_mobile.di.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val sessionStore: SessionStore
) : ViewModel() {

    val themeMode = sessionStore.getThemeModeFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "system")

    fun setThemeMode(mode: String) {
        viewModelScope.launch { sessionStore.saveThemeMode(mode) }
    }
}
