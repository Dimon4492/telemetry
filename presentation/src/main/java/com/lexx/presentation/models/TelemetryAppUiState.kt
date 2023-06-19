package com.lexx.presentation.models

import com.lexx.presentation.navigation.NavigationAppContentType

data class TelemetryAppUiState(
    val name: String,
    val currentTelemetryAppContent: NavigationAppContentType
)
