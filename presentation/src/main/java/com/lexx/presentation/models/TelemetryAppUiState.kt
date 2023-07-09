package com.lexx.presentation.models

import com.lexx.presentation.navigation.NavigationAppContentType

data class TelemetryAppUiState(
    val currentTelemetryAppContent: NavigationAppContentType = NavigationAppContentType.PLOT_HOUR_CONTENT_TYPE,
)
