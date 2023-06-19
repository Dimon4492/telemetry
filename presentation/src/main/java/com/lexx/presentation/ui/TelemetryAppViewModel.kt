package com.lexx.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.presentation.models.TelemetryAppUiState
import com.lexx.presentation.navigation.NavigationAppContentType
import com.lexx.presentation.navigation.NavigationAppContentType.SENSORS_CONTENT_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelemetryAppViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        TelemetryAppUiState("Noname", SENSORS_CONTENT_TYPE)
    )

    val uiState: StateFlow<TelemetryAppUiState> = _uiState.asStateFlow()

    fun updateNavigationContent(navigationAppContentType: NavigationAppContentType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(currentTelemetryAppContent = navigationAppContentType)
        }
    }
}
