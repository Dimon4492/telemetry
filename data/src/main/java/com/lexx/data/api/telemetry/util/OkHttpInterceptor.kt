package com.lexx.data.api.telemetry.util

import com.lexx.data.BuildConfig
import com.lexx.domain.features.settings.SettingsRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class OkHttpInterceptor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        lateinit var serverAddress: String
        runBlocking {
            serverAddress = settingsRepository.getServerAddress(BuildConfig.DEFAULT_BASE_URL)
        }

        val serverPath: String = request.url().encodedPath()
        val url = "http://${serverAddress}${serverPath}"
        val newRequest = request
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
