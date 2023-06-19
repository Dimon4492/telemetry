package com.lexx.data.features.sensors.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.features.sensors.SensorsInfoRemoteDataSource
import com.lexx.domain.models.SENSORS_SECONDS_REFRESH_PERIOD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException
import javax.inject.Inject

class WebserviceSensorsInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val BASE_URL : String
) : SensorsInfoRemoteDataSource {

    override val sensorsInfo: Flow<Result<List<SensorInfoDto>>> = flow {
        while(true) {
            try {
                val sensorsInfo = telemetryApiService.getSensorsInfo(("$BASE_URL/sensors"))
                emit(Result.success(sensorsInfo))
            } catch (e: ConnectException) {
                emit(Result.failure(e))
            }
            delay(SENSORS_SECONDS_REFRESH_PERIOD * 1000L)
        }
    }
}
