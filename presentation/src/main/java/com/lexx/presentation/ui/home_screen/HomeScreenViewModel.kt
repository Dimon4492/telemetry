package com.lexx.presentation.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.presentation.models.home_screen.HomeScreenUiState
import com.lexx.presentation.ui.navigation.AppContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val sensorsRepository: SensorsRepository,
    private val plotRepository: PlotRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeScreenUiState()
    )

    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun updateAppContent(appContentType: AppContentType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(currentTelemetryAppContent = appContentType)

            plotRepository.pauseHourPolling(appContentType != AppContentType.PLOT_HOUR_CONTENT_TYPE)
            plotRepository.pauseSixHoursPolling(appContentType != AppContentType.PLOT_SIX_HOURS_CONTENT_TYPE)
            plotRepository.pauseDayPolling(appContentType != AppContentType.PLOT_DAY_CONTENT_TYPE)

            sensorsRepository.pauseSensorPolling(appContentType != AppContentType.SENSORS_CONTENT_TYPE)
        }
    }
}
