package com.lexx.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lexx.presentation.ui.home_screen.HomeScreen
import com.lexx.presentation.ui.navigation.AppNavigationType

@Composable
fun TelemetryApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {
    val appNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            AppNavigationType.BOTTOM_NAVIGATION
        }
        else -> {
            AppNavigationType.NAVIGATION_RAIL
        }
    }

    HomeScreen(
        modifier = modifier,
        appNavigationType = appNavigationType,
    )
}
