package com.lexx.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.presentation.models.TelemetryAppUiState
import com.lexx.presentation.navigation.NavigationAppContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelemetryAppViewModel @Inject constructor(
    private val sensorsRepository: SensorsRepository,
    private val plotRepository: PlotRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        TelemetryAppUiState()
    )

    val uiState: StateFlow<TelemetryAppUiState> = _uiState.asStateFlow()

    fun updateNavigationContent(navigationAppContentType: NavigationAppContentType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(currentTelemetryAppContent = navigationAppContentType)

            plotRepository.pauseHourPolling(navigationAppContentType != NavigationAppContentType.PLOT_HOUR_CONTENT_TYPE)
            plotRepository.pauseSixHoursPolling(navigationAppContentType != NavigationAppContentType.PLOT_SIX_HOURS_CONTENT_TYPE)
            plotRepository.pauseDayPolling(navigationAppContentType != NavigationAppContentType.PLOT_DAY_CONTENT_TYPE)

            sensorsRepository.pauseSensorPolling(navigationAppContentType != NavigationAppContentType.SENSORS_CONTENT_TYPE)
        }
    }

    fun onPause() {
        sensorsRepository.pauseNetworkPolling(true)
        plotRepository.pauseNetworkPolling(true)
    }

    fun onResume() {
        sensorsRepository.pauseNetworkPolling(false)
        plotRepository.pauseNetworkPolling(false)
    }
}
