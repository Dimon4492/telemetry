package com.lexx.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.navigation.TelemetryAppNavigationType

@Composable
fun TelemetryApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val navigationType: TelemetryAppNavigationType

    val viewModel: TelemetryAppViewModel = viewModel()
    val telemetryAppUiState = viewModel.uiState.collectAsState().value

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = TelemetryAppNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = TelemetryAppNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = TelemetryAppNavigationType.NAVIGATION_RAIL
        }
        else -> {
            navigationType = TelemetryAppNavigationType.BOTTOM_NAVIGATION
        }
    }
    TelemetryHomeScreen(
        navigationType,
        telemetryAppUiState,
        onTabPressed = {navigationAppContentType ->
            viewModel.updateNavigationContent(navigationAppContentType)
        },
        modifier=modifier)
}
