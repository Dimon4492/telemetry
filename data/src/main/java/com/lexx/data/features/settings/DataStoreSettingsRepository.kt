package com.lexx.data.features.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import com.lexx.domain.features.settings.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : SettingsRepository {
    private val serverAddressPreferencesKey = stringPreferencesKey(name = "server_address")

    override suspend fun setServerAddress(serverAddress: String) {
        setStringPreference(serverAddressPreferencesKey, serverAddress)
    }

    override suspend fun getServerAddress(
        defaultServerAddress: String
    ): String {
        return getStringPreference(serverAddressPreferencesKey)
    }

    private suspend fun setStringPreference(
        preferenceKey: Key<String>,
        preferenceValue: String
    ) {
        dataStorePreferences.edit { it ->
            it[preferenceKey] = preferenceValue
        }
    }

    private suspend fun getStringPreference(
        preferenceKey: Key<String>,
        preferenceDefaultValue: String = ""
    ): String {
        return try {
            dataStorePreferences.data.first()[preferenceKey] ?: preferenceDefaultValue
        } catch (e: Exception) {
            throw e
        }
    }
}
