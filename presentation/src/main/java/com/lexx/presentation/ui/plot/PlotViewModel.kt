package com.lexx.presentation.ui.plot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.PLOT_X_STEPS
import com.lexx.domain.PLOT_Y_STEPS
import com.lexx.domain.features.plot.GetPlotInfoUseCase
import com.lexx.domain.models.PlotInfo
import com.lexx.presentation.mapping.UiMapper
import com.lexx.presentation.models.PlotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class PlotViewModel @Inject constructor(
    val getPlotInfoUseCase: GetPlotInfoUseCase,
    private val uiMapper: UiMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlotUiState())
    val uiState: StateFlow<PlotUiState> = _uiState.asStateFlow()

    init {
        loadSensors()
    }

    private fun loadSensors() {
        viewModelScope.launch {
            getPlotInfoUseCase().collect { result ->
                if (result.isSuccess) {
                    val plotInfo = result.getOrDefault(PlotInfo())
                    val yStep = (plotInfo.maxValue - plotInfo.minValue) / PLOT_Y_STEPS
                    val xStep = (plotInfo.maxTimestamp - plotInfo.minTimestamp) / PLOT_X_STEPS
                    _uiState.value = _uiState.value.copy(
                        connectionError = false,
                        plotInfo = plotInfo,
                        verticalStep = yStep,
                        yValues = (0..PLOT_Y_STEPS).map { uiMapper.mapValueToScreen(plotInfo.minValue + it * yStep) },
                        xValues = (0..PLOT_X_STEPS).map { uiMapper.mapTimestampToScreen(plotInfo.minTimestamp + it * xStep) },
                    )
                } else {
                    _uiState.value = _uiState.value.copy(connectionError = true)
                }
            }
        }
    }
}
