package com.lexx.presentation.models

import com.lexx.domain.PLOT_Y_STEPS
import com.lexx.domain.models.PlotInfo

private val defaultVerticalStep = 50f
data class PlotUiState (
    val plotInfo: PlotInfo = PlotInfo(),
    val xAxisLabels: List<String> = listOf(),
    val yAxisLabels: List<String> = listOf(),
    val connectionError: Boolean = false,
    val xValues: List<String> = (0..9).map { "" },
    val yValues: List<String> = (0..PLOT_Y_STEPS).map { "" },
    val verticalStep: Float = defaultVerticalStep,
)
