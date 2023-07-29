package com.lexx.presentation.models.plot

import androidx.compose.ui.graphics.Color

data class PlotLineUiInfo(
    val nameId: Int,
    val values: List<PlotUiData>,
    val color: Color,
    val enabled: Boolean,
    val multiplier: Float,
)