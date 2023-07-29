package com.lexx.presentation.mapping

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.PlotData
import com.lexx.domain.models.PlotInfo
import com.lexx.domain.models.PlotLineInfo
import com.lexx.domain.models.SensorInfo
import com.lexx.presentation.models.plot.PlotLineUiInfo
import com.lexx.presentation.models.plot.PlotUiData
import com.lexx.presentation.models.plot.PlotUiInfo
import com.lexx.presentation.models.sensors.SensorUiInfo
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class UiMapper @Inject constructor(
    private val sensorValueFormatter: DecimalFormat,
    private val simpleDateFormat: SimpleDateFormat,
) {
    fun mapValueToScreen(fl: Float): String {
        return sensorValueFormatter.format(fl)
    }

    fun mapTimestampToScreen(timestamp: Long): String {
        val netDate = Date(timestamp * 1000 - 6 * 60 * 60 * 1000)
        return simpleDateFormat.format(netDate)
    }

    fun mapColorToString(color: Color): String {
        val hexColor = java.lang.String.format("#%08X", color.toArgb())
        return hexColor
    }

    fun mapSensorInfoToUi(sensorsInfo: List<SensorInfo>, sensorsLocalInfo: List<SensorLocalInfo>): List<SensorUiInfo> {
        val localInfoMap: Map<Int, SensorLocalInfo> = sensorsLocalInfo.map {
            it.remoteSensorId to it
        }.toMap()

        val result = sensorsInfo.map {
            var uiInfo = SensorUiInfo(
                nameId = it.nameId,
                description = it.name,
                remoteName = it.name,
                lastValue = it.lastValue,
                lastTimestamp = it.lastTimestamp,
            )
            val localInfo = localInfoMap[it.nameId]
            if ( localInfo != null) {
                uiInfo.copy(
                    color = mapStringToColor(localInfo.color),
                    description = localInfo.description,
                    enabled = localInfo.enabled,
                    multiplier = localInfo.multiplier.toString()
                )
            } else {
                uiInfo
            }
        }
        return result
    }

    private fun mapStringToColor(color: String): Color {
        val result = Color(android.graphics.Color.parseColor(color))
        return result
    }

    fun mapPlotInfoToUi(plotInfo: PlotInfo, localInfo: Map<Int, SensorLocalInfo>): PlotUiInfo {
        if (plotInfo.values.isEmpty()) {
            return PlotUiInfo()
        }
        var minValue = Float.MAX_VALUE
        var maxValue = Float.MIN_VALUE
        var minTimestamp = Long.MAX_VALUE
        var maxTimestamp = Long.MIN_VALUE
        for (line in plotInfo.values) {
            val localSensorInfo = localInfo[line.nameId]
            if (localSensorInfo != null) {
                if (!localSensorInfo.enabled) {
                    continue
                }
            }
            val multiplier = localSensorInfo?.multiplier ?: 1F

                for (data in line.values) {
                with(data) {
                    val v = if (multiplier == 1F) {value} else {value * multiplier}
                    if (minValue > v) {
                        minValue = v
                    }

                    if (maxValue < v) {
                        maxValue = v
                    }

                    if (minTimestamp > timestamp) {
                        minTimestamp = timestamp
                    }

                    if (maxTimestamp < timestamp) {
                        maxTimestamp = timestamp
                    }
                }
            }
        }

        return PlotUiInfo(
            values = mapLinesToUi(plotInfo.values, localInfo),
            minValue = minValue,
            maxValue = maxValue,
            minTimestamp = minTimestamp,
            maxTimestamp = maxTimestamp,
            connectionError = plotInfo.errorMessage.isNotEmpty(),
            errorMessage = plotInfo.errorMessage,
            noDataError = plotInfo.values.isEmpty(),
        )
    }

    private fun mapLinesToUi(
        lines: List<PlotLineInfo>,
        localInfo: Map<Int, SensorLocalInfo>
    ): List<PlotLineUiInfo> {

        return lines.map {plotInfo ->
            val localPlotInfo = localInfo[plotInfo.nameId]
            val color = mapStringToColor(localPlotInfo?.color ?: "#FF000000")
            val multiplier = localPlotInfo?.multiplier ?: 1F
            val values = mapValuesToUi(plotInfo.values, multiplier)
            PlotLineUiInfo(
                nameId = plotInfo.nameId,
                values = values,
                color = color,
                enabled = localPlotInfo?.enabled ?: true,
                multiplier = multiplier
            )
        }
    }

    private fun mapValuesToUi(values: List<PlotData>, multiplier: Float): List<PlotUiData> {
        return values.map {
            mapPlotDataToUi(it, multiplier)
        }
    }

    private fun mapPlotDataToUi(plotData: PlotData, multiplier: Float): PlotUiData {
        if (multiplier != 1F) {
            return PlotUiData(
                value = plotData.value * multiplier,
                timestamp = plotData.timestamp
            )
        } else {
            return PlotUiData(
                value = plotData.value,
                timestamp = plotData.timestamp
            )
        }
    }

    fun mapSensorLocalInfoFromUi(selectedSensorInfo: SensorUiInfo): SensorLocalInfo {
           return with(selectedSensorInfo) {
            SensorLocalInfo (
                remoteSensorId = nameId,
                color = mapColorToString(color),
                description = description,
                enabled = enabled,
                multiplier = multiplier.toFloatOrNull() ?: 1F,
            )
        }
    }
}
