package com.lexx.presentation.ui.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lexx.presentation.ui.navigation.AppContentType
import com.lexx.presentation.ui.sensors.SensorsPage
import com.lexx.presentation.ui.settings.SettingsPage
import com.lexx.telemetry.ui.plot.DayPlotPage
import com.lexx.telemetry.ui.plot.HourPlotPage
import com.lexx.telemetry.ui.plot.SixHoursPlotPage

@Composable
fun AppContent(
    modifier: Modifier,
    contentType: AppContentType
) {
    when (contentType) {
        AppContentType.PLOT_HOUR_CONTENT_TYPE ->
            HourPlotPage(modifier)

        AppContentType.PLOT_SIX_HOURS_CONTENT_TYPE ->
            SixHoursPlotPage(modifier)

        AppContentType.PLOT_DAY_CONTENT_TYPE ->
            DayPlotPage(modifier)

        AppContentType.SETTINGS_CONTENT_TYPE ->
            SettingsPage(modifier)

        AppContentType.SENSORS_CONTENT_TYPE ->
            SensorsPage(modifier)
    }
}
