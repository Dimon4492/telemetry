package com.lexx.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.settings.GetServerAddressUseCase
import com.lexx.domain.features.settings.SetServerAddressUseCase
import com.lexx.presentation.models.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getServerAddressUseCase: GetServerAddressUseCase,
    private val setServerAddressUseCase: SetServerAddressUseCase
) : ViewModel() {
    private val defaultServerAddress = "192.168.0.166:9090"
    private val _uiState = MutableStateFlow(SettingsUiState(serverAddress = defaultServerAddress))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val serverAddress = getServerAddressUseCase()
            _uiState.value = _uiState.value.copy(serverAddress = serverAddress)
        }
    }

    fun setServerAddress(serverAddress: String) {
        _uiState.value = _uiState.value.copy(serverAddress = serverAddress)

        viewModelScope.launch {
            setServerAddressUseCase(serverAddress)
        }
    }
}
