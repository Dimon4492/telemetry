package com.lexx.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.settings.GetServerAddressUseCase
import com.lexx.domain.features.settings.SetServerAddressUseCase
import com.lexx.presentation.models.settings.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getServerAddressUseCase: GetServerAddressUseCase,
    private val setServerAddressUseCase: SetServerAddressUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = _uiState.value.copy(serverAddress = getServerAddressUseCase())
            }
        }
    }

    fun setServerAddress(serverAddress: String) {
        _uiState.value = _uiState.value.copy(serverAddress = serverAddress)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setServerAddressUseCase(serverAddress)
            }
        }
    }
}
