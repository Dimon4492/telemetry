package com.lexx.presentation.mapping

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class UiMapper @Inject constructor(
    private val sensorValueFormatter: DecimalFormat
) {
    fun mapValueToScreen(fl: Float): String {
        return sensorValueFormatter.format(fl)
    }

    fun mapTimestampToScreen(timestamp: Long): String {
        try {
            val sdf = SimpleDateFormat("HH:mm:ss")
            val netDate = Date(timestamp * 1000 - 6 * 60 * 60 * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}
