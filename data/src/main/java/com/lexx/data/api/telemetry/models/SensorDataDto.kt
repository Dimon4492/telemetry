package com.lexx.data.api.telemetry.models

data class SensorDataDto (
    val valueId: Int,
    val nameId: Int,
    val value: Float,
    val timestamp: String
)
