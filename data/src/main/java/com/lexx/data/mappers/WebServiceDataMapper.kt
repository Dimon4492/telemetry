package com.lexx.data.mappers

import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.domain.models.PlotData
import com.lexx.domain.models.PlotInfo
import com.lexx.domain.models.PlotLineInfo
import com.lexx.domain.models.SensorInfo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WebServiceDataMapper @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter
) {
    fun mapSensors(sensorsInfoDto: List<SensorInfoDto>): List<SensorInfo> {
        return sensorsInfoDto.map {
            mapSensor(sensorInfoDto = it)
        }
    }

    private fun mapSensor(sensorInfoDto: SensorInfoDto): SensorInfo {
        return with(sensorInfoDto) {
            SensorInfo(
                nameId = nameId,
                name = name,
                lastValue = lastValue.toString(),
                lastTimestamp = lastTimestamp
            )
        }
    }

    private fun mapStringTimeToLong(ts: String) : Long {
        return LocalDateTime
            .parse(ts, dateTimeFormatter)
            .toEpochSecond(ZoneOffset.UTC)
    }

    fun mapPlotInfo(sensorsData: List<SensorDataDto>): PlotInfo {
        if (sensorsData.isEmpty()) {
            return PlotInfo()
        }

        val points: MutableMap<Int, MutableList<PlotData>> = mutableMapOf()
        var minValue = Float.MAX_VALUE
        var maxValue = Float.MIN_VALUE
        var minTimestamp = mapStringTimeToLong(sensorsData[0].timestamp)
        var maxTimestamp = minTimestamp

        for (data in sensorsData) {
            if (data.nameId !in points) {
                points[data.nameId] = mutableListOf()
            }
            val ts = mapStringTimeToLong(data.timestamp)
            points[data.nameId]?.add(PlotData(data.value, ts))

            if (maxValue < data.value) {
                maxValue = data.value
            }

            if (minValue > data.value) {
                minValue = data.value
            }

            if (maxTimestamp < ts) {
                maxTimestamp = ts
            }

            if (minTimestamp > ts) {
                minTimestamp = ts
            }
        }

        val values = points.map {
            PlotLineInfo(
                nameId = it.key,
                values = it.value
            )
        }

        return PlotInfo(
            values = values,
        )
    }
}
