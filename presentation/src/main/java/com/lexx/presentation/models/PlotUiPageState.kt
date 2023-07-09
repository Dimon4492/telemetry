package com.lexx.presentation.models

import com.lexx.domain.PLOT_Y_STEPS

private val defaultVerticalStep = 50f

data class PlotUiPageState (
    val plotInfo: PlotUiInfo = PlotUiInfo(),
    val xAxisLabels: List<String> = listOf(),
    val yAxisLabels: List<String> = listOf(),
    val connectionError: Boolean = false,
    val errorMessage: String = "",
    val noDataError: Boolean = true,
    val xValues: List<String> = (0..9).map { "" },
    val yValues: List<String> = (0..PLOT_Y_STEPS).map { "" },
    val verticalStep: Float = defaultVerticalStep,
)
