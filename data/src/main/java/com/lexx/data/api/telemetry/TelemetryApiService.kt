package com.lexx.data.api.telemetry

import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.api.telemetry.models.SensorInfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface TelemetryApiService {
    @GET("/sensors")
    suspend fun getSensorsInfo(): List<SensorInfoDto>

    @GET("/data/{interval}")
    suspend fun getSensorsData(@Path("interval") interval: String): List<SensorDataDto>
}
