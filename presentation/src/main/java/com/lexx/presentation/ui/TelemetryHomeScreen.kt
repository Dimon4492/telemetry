package com.lexx.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lexx.presentation.R
import com.lexx.presentation.models.TelemetryAppUiState
import com.lexx.presentation.navigation.NavigationAppContentType
import com.lexx.presentation.navigation.TelemetryAppNavigationType
import com.lexx.presentation.ui.sensors.SensorsPage
import com.lexx.presentation.ui.settings.SettingsPage
import com.lexx.telemetry.ui.plot.PlotPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelemetryHomeScreen(
    navigationType: TelemetryAppNavigationType,
    telemetryAppUiState: TelemetryAppUiState,
    onTabPressed: ((NavigationAppContentType) -> Unit),
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            navigationAppContentType = NavigationAppContentType.PLOT_CONTENT_TYPE,
            icon = R.drawable.twotone_stacked_line_chart_24
        ),
        NavigationItemContent(
            navigationAppContentType = NavigationAppContentType.SENSORS_CONTENT_TYPE,
            icon = R.drawable.twotone_sensors_24,
        ),
        NavigationItemContent(
            navigationAppContentType = NavigationAppContentType.SETTINGS_CONTENT_TYPE,
            icon = R.drawable.twotone_settings_24,
        )
    )

    val title = when (telemetryAppUiState.currentTelemetryAppContent) {
        NavigationAppContentType.PLOT_CONTENT_TYPE-> stringResource(id = R.string.plot_page_title)
        NavigationAppContentType.SENSORS_CONTENT_TYPE-> stringResource(id = R.string.sensors_page_title)
        NavigationAppContentType.SETTINGS_CONTENT_TYPE-> stringResource(id = R.string.settings_page_title)
    }

    Scaffold(
        content = {
            TelemetryAppContent(
                navigationType,
                telemetryAppUiState.currentTelemetryAppContent,
                navigationItemContentList,
                telemetryAppUiState,
                onTabPressed,
                modifier.padding(it))
        },
        topBar = { TopAppBar(title = {Text(title)})}
    )
}

@Composable
private fun TelemetryAppContent(
    navigationType: TelemetryAppNavigationType,
    navigationAppContentType: NavigationAppContentType,
    navigationItemContentList: List<NavigationItemContent>,
    telemetryAppUiState: TelemetryAppUiState,
    onTabPressed: ((NavigationAppContentType) -> Unit),
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == TelemetryAppNavigationType.NAVIGATION_RAIL) {
                TelemetryAppNavigationRail(
                    currentTab = telemetryAppUiState.currentTelemetryAppContent,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                when (navigationAppContentType) {
                    NavigationAppContentType.PLOT_CONTENT_TYPE ->
                        PlotPage(modifier = Modifier.weight(1f))

                    NavigationAppContentType.SETTINGS_CONTENT_TYPE ->
                        SettingsPage(modifier = Modifier.weight(1f))

                    NavigationAppContentType.SENSORS_CONTENT_TYPE ->
                        SensorsPage(modifier = Modifier.weight(1f))
                }

                AnimatedVisibility(
                    visible = navigationType == TelemetryAppNavigationType.BOTTOM_NAVIGATION
                ) {
                    TelemetryAppBottomNavigationBar(
                        currentTab = telemetryAppUiState.currentTelemetryAppContent,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                    )
                }
            }
        }
    }
}

@Composable
private fun TelemetryAppNavigationRail(
    currentTab: NavigationAppContentType,
    onTabPressed: ((NavigationAppContentType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.navigationAppContentType,
                onClick = { onTabPressed(navItem.navigationAppContentType) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        null
                    )
                }
            )
        }
    }
}

@Composable
private fun TelemetryAppBottomNavigationBar(
    currentTab: NavigationAppContentType,
    onTabPressed: ((NavigationAppContentType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.navigationAppContentType,
                onClick = { onTabPressed(navItem.navigationAppContentType) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

private data class NavigationItemContent (
    val navigationAppContentType: NavigationAppContentType,
    val icon: Int,
)
