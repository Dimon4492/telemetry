package com.lexx.data.api.telemetry.models

data class SensorInfoDto(
    val nameId: Int,
    val name: String,
    val lastValue: Float,
    val lastTimestamp: String
)
