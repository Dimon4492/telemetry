package com.lexx.presentation.ui.plot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.PLOT_X_STEPS
import com.lexx.domain.PLOT_Y_STEPS
import com.lexx.domain.features.plot.GetDayPlotInfoUseCase
import com.lexx.domain.features.plot.GetHourPlotInfoUseCase
import com.lexx.domain.features.plot.GetSixHoursPlotInfoUseCase
import com.lexx.domain.features.sensors.GetSensorsLocalInfoUseCase
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.PlotInfo
import com.lexx.presentation.mapping.UiMapper
import com.lexx.presentation.models.plot.PlotUiInfo
import com.lexx.presentation.models.plot.PlotUiPageState
import com.lexx.presentation.models.plot.PlotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlotViewModel @Inject constructor(
    val getHourPlotInfoUseCase: GetHourPlotInfoUseCase,
    val getSixHoursPlotInfoUseCase: GetSixHoursPlotInfoUseCase,
    val getDayPlotInfoUseCase: GetDayPlotInfoUseCase,
    val getSensorsLocalInfoUseCase: GetSensorsLocalInfoUseCase,
    private val uiMapper: UiMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlotUiState())
    val uiState: StateFlow<PlotUiState> = _uiState.asStateFlow()

    init {
        loadSensors()
    }

    private fun combinePlotUiInfo(remoteResult: Result<PlotInfo>, sensorsLocalInfo: List<SensorLocalInfo>): PlotUiInfo {
        return if (remoteResult.isSuccess) {
            val plotInfo = remoteResult.getOrDefault(PlotInfo())
            if (plotInfo.values.isEmpty()) {
                PlotUiInfo(noDataError = true )
            } else {
                val sensorsLocalInfoMap: Map<Int, SensorLocalInfo> =
                    sensorsLocalInfo.map {
                        it.remoteSensorId to it
                    }.toMap()

                uiMapper.mapPlotInfoToUi(plotInfo, sensorsLocalInfoMap)
            }
        } else {
            PlotUiInfo(
                noDataError = false,
                connectionError = true,
                errorMessage = remoteResult.toString()
            )
        }
    }

    private fun updateState(plotInfo: PlotUiInfo, plotUiPageState: PlotUiPageState): PlotUiPageState {
        return if (plotInfo.connectionError || plotInfo.noDataError) {
            plotUiPageState.copy(
                noDataError = plotInfo.noDataError,
                connectionError = plotInfo.connectionError,
                errorMessage = plotInfo.errorMessage,
            )
        } else {
            val yStep = (plotInfo.maxValue - plotInfo.minValue) / PLOT_Y_STEPS
            val xStep = (plotInfo.maxTimestamp - plotInfo.minTimestamp) / PLOT_X_STEPS
            plotUiPageState.copy(
                connectionError = false,
                noDataError = false,
                plotInfo = plotInfo,
                verticalStep = yStep,
                yValues = (0..PLOT_Y_STEPS).map { uiMapper.mapValueToScreen(plotInfo.minValue + it * yStep) },
                xValues = (0..PLOT_X_STEPS).map { uiMapper.mapTimestampToScreen(plotInfo.minTimestamp + it * xStep) },
            )
        }
    }

    private fun loadSensors() {
        viewModelScope.launch {
            getHourPlotInfoUseCase()
                .combine(getSensorsLocalInfoUseCase()) { remoteResult: Result<PlotInfo>, sensorsLocalInfo: List<SensorLocalInfo> ->
                    combinePlotUiInfo(remoteResult, sensorsLocalInfo)
                }
                .collect { plotInfo: PlotUiInfo ->
                    _uiState.value = _uiState.value.copy(hourPlotUiPageState = updateState(plotInfo, _uiState.value.hourPlotUiPageState))
                }
        }

        viewModelScope.launch {
            getSixHoursPlotInfoUseCase()
                .combine(getSensorsLocalInfoUseCase()) { remoteResult: Result<PlotInfo>, sensorsLocalInfo: List<SensorLocalInfo> ->
                    combinePlotUiInfo(remoteResult, sensorsLocalInfo)
                }
                .collect { plotInfo: PlotUiInfo ->
                    _uiState.value = _uiState.value.copy(sixHoursPlotUiPageState = updateState(plotInfo, _uiState.value.sixHoursPlotUiPageState))
                }
        }

        viewModelScope.launch {
            getDayPlotInfoUseCase()
                .combine(getSensorsLocalInfoUseCase()) { remoteResult: Result<PlotInfo>, sensorsLocalInfo: List<SensorLocalInfo> ->
                    combinePlotUiInfo(remoteResult, sensorsLocalInfo)
                }
                .collect { plotInfo: PlotUiInfo ->
                    _uiState.value = _uiState.value.copy(dayPlotUiPageState = updateState(plotInfo, _uiState.value.dayPlotUiPageState))
                }
        }
    }
}
