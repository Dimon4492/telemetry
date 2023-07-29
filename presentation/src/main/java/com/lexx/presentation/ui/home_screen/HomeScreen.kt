package com.lexx.presentation.ui.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.R
import com.lexx.presentation.models.navigation.NavigationItemContent
import com.lexx.presentation.ui.navigation.AppContentType
import com.lexx.presentation.ui.navigation.AppNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    appNavigationType: AppNavigationType = AppNavigationType.NAVIGATION_RAIL,
    viewModel: HomeScreenViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value

    val navigationItemContentList = listOf(
        NavigationItemContent(
            appContentType = AppContentType.PLOT_HOUR_CONTENT_TYPE,
            tabIcon = R.drawable.twotone_stacked_line_chart_24,
            tabLabel = stringResource(id = R.string.one_hour_title),
            pageTitle = stringResource(id = R.string.plot_hour_page_title),
        ),
        NavigationItemContent(
            appContentType = AppContentType.PLOT_SIX_HOURS_CONTENT_TYPE,
            tabIcon = R.drawable.twotone_stacked_line_chart_24,
            tabLabel = stringResource(id = R.string.six_hour_title),
            stringResource(id = R.string.plot_six_hours_page_title),
        ),
        NavigationItemContent(
            appContentType = AppContentType.PLOT_DAY_CONTENT_TYPE,
            tabIcon = R.drawable.twotone_stacked_line_chart_24,
            tabLabel = stringResource(id = R.string.one_day_title),
            stringResource(id = R.string.plot_day_page_title)
        ),
        NavigationItemContent(
            appContentType = AppContentType.SENSORS_CONTENT_TYPE,
            tabIcon = R.drawable.twotone_sensors_24,
            tabLabel = "",
            pageTitle = stringResource(id = R.string.sensors_page_title),
        ),
        NavigationItemContent(
            appContentType = AppContentType.SETTINGS_CONTENT_TYPE,
            tabIcon = R.drawable.twotone_settings_24,
            tabLabel = "",
            pageTitle = stringResource(id = R.string.settings_page_title),
        )
    )

    val title = navigationItemContentList.find { navigationItemContent ->
        navigationItemContent.appContentType == uiState.currentTelemetryAppContent
    }?.pageTitle ?: ""

    Scaffold(
        content = {
            NavigationLayout(
                appNavigationType,
                uiState.currentTelemetryAppContent,
                navigationItemContentList,
                modifier = modifier.padding(it),
                onTabPressed = {
                    viewModel.updateAppContent(it)
                },
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(title)
                }
            )
        }
    )
}
