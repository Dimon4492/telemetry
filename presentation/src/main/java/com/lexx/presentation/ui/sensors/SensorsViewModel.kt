package com.lexx.presentation.ui.sensors

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.sensors.GetSensorsInfoUseCase
import com.lexx.presentation.R
import com.lexx.presentation.models.SensorsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorsViewModel @Inject constructor(
    val getSensorsInfoUseCase: GetSensorsInfoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SensorsUiState())
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeSensorsInfo()
    }

    fun observeSensorsInfo() {
        viewModelScope.launch {
            getSensorsInfoUseCase().collect{ result ->
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        connectionError = false,
                        sensors = result.getOrDefault(listOf())
                    )
                } else {
                    _uiState.value = _uiState.value.copy(connectionError = true)
                }
            }
        }
    }
}
