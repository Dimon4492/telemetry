package com.lexx.data.features.sensors.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.features.sensors.SensorsInfoRemoteDataSource
import com.lexx.domain.SENSORS_SECONDS_REFRESH_PERIOD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class WebserviceSensorsInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
) : SensorsInfoRemoteDataSource {

    private var paused = false
    private var sensorsPaused = false
    override fun pauseNetworkPolling(pause: Boolean) {
        paused = pause
    }

    override fun pauseSensorPolling(pause: Boolean) {
        sensorsPaused = pause
    }

    override val sensorsInfo: Flow<Result<List<SensorInfoDto>>> = flow {
        while(true) {
            if (!paused && !sensorsPaused) {
                emit(Result.runCatching {
                    withContext(Dispatchers.IO) {
                        telemetryApiService.getSensorsInfo()
                    }
                })
            }
            delay(SENSORS_SECONDS_REFRESH_PERIOD * 1000L)
        }
    }
}
