package com.lexx.data.features.sensors.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.features.sensors.SensorsInfoRemoteDataSource
import com.lexx.domain.SENSORS_SECONDS_REFRESH_PERIOD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class WebserviceSensorsInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
) : SensorsInfoRemoteDataSource {

    private var paused = false
    override fun pauseNetworkPolling() {
        paused = true
    }

    override fun resumeNetworkPolling() {
        paused = false
    }

    override val sensorsInfo: Flow<Result<List<SensorInfoDto>>> = flow {
        while(true) {
            if (!paused) {
                try {
                    val sensorsInfo = telemetryApiService.getSensorsInfo()
                    emit(Result.success(sensorsInfo))
                } catch (e: UnknownHostException) {
                    emit(Result.failure(e))
                } catch (e: ConnectException) {
                    emit(Result.failure(e))
                } catch (e: Exception) {
                    Timber.d("Get remote SensorInfo exception: $e")
                    throw e
                }
            }
            delay(SENSORS_SECONDS_REFRESH_PERIOD * 1000L)
        }
    }
}
