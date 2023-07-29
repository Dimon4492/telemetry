package com.lexx.domain.features.settings

interface SettingsRepository {
    suspend fun getServerAddress(): String
    suspend fun setServerAddress(serverAddress: String)
}
