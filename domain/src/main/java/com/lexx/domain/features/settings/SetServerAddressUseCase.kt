package com.lexx.domain.features.settings

import javax.inject.Inject

class SetServerAddressUseCase @Inject constructor(
    private val repository: SettingsRepository
){
    suspend operator fun invoke(serverAddress: String) {
        repository.setServerAddress(serverAddress = serverAddress)
    }
}
