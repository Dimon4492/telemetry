package com.lexx.presentation.models.home_screen

import com.lexx.presentation.ui.navigation.AppContentType

data class HomeScreenUiState(
    val currentTelemetryAppContent: AppContentType = AppContentType.PLOT_HOUR_CONTENT_TYPE,
)
