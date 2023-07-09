package com.lexx.presentation.models

import androidx.compose.ui.graphics.Color

data class SensorUiInfo (
    val nameId: Int = -1,
    val description: String = "",
    val remoteName: String = "",
    val lastValue: String = "",
    val lastTimestamp: String = "",
    val color: Color = Color.Black,
    val enabled: Boolean = true,
    val multiplier: String = "",
)
