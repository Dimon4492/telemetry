package com.lexx.presentation.models.plot

data class PlotUiState (
    val hourPlotUiPageState: PlotUiPageState = PlotUiPageState(),
    val sixHoursPlotUiPageState: PlotUiPageState = PlotUiPageState(),
    val dayPlotUiPageState: PlotUiPageState = PlotUiPageState(),
)