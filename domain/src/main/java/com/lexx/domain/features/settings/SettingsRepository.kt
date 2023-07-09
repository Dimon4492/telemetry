package com.lexx.domain.features.settings

interface SettingsRepository {
    suspend fun setServerAddress(serverAddress: String)
    suspend fun getServerAddress(): String
}
