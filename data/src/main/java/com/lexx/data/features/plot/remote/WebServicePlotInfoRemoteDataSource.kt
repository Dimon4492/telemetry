package com.lexx.data.features.plot.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.features.plot.PlotInfoRemoteDataSource
import com.lexx.domain.PLOT_SECONDS_REFRESH_PERIOD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class WebServicePlotInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
) : PlotInfoRemoteDataSource {

    private var paused = false
    override fun pauseNetworkPolling() {
        paused = true
    }

    override fun resumeNetworkPolling() {
        paused = false
    }

    override val sensorsData: Flow<Result<List<SensorDataDto>>> = flow {
        while(true) {
            if (!paused) {
                try {
                    val sensorsData = telemetryApiService.getSensorsData()
                    emit(Result.success(sensorsData))
                } catch (e: UnknownHostException) {
                    emit(Result.failure(e))
                } catch (e: ConnectException) {
                    emit(Result.failure(e))
                } catch (e: SocketTimeoutException) {
                    emit(Result.failure(e))
                } catch (e: Exception) {
                    Timber.d("get remote SensorData exception: $e")
                    throw e
                }
            }
            delay(PLOT_SECONDS_REFRESH_PERIOD * 1000L)
        }
    }
}
