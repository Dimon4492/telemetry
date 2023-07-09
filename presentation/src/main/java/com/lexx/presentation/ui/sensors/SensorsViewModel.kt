package com.lexx.presentation.ui.sensors

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.sensors.GetSensorsInfoUseCase
import com.lexx.domain.features.sensors.GetSensorsLocalInfoUseCase
import com.lexx.domain.features.sensors.SetSensorLocalInfoUseCase
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.SensorInfo
import com.lexx.presentation.mapping.UiMapper
import com.lexx.presentation.models.SensorUiInfo
import com.lexx.presentation.models.SensorsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SensorsViewModel @Inject constructor(
    val getSensorsInfoUseCase: GetSensorsInfoUseCase,
    val getSensorsLocalInfoUseCase: GetSensorsLocalInfoUseCase,
    val setSensorLocalInfoUseCase: SetSensorLocalInfoUseCase,
    val uiMapper: UiMapper,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SensorsUiState())
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeSensorsInfo()
    }

    private fun observeSensorsInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getSensorsInfoUseCase()
                    .combine(getSensorsLocalInfoUseCase()) { info: Result<List<SensorInfo>>, localInfo: List<SensorLocalInfo> ->
                        combineSensorsUiInfo(info, localInfo)
                    }.collect {
                        if (it.connectionError) {
                            _uiState.value = _uiState.value.copy(
                                noSensorsError = false,
                                connectionError = true,
                                errorMessage = it.errorMessage
                            )
                        } else if (it.sensors.isEmpty()) {
                            _uiState.value = _uiState.value.copy(
                                noSensorsError = true,
                                connectionError = false,
                                errorMessage = ""
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                noSensorsError = false,
                                connectionError = false,
                                sensors = it.sensors,
                                errorMessage = ""
                            )
                        }
                    }
            }
        }
    }

    private fun combineSensorsUiInfo(
        info: Result<List<SensorInfo>>,
        localInfo: List<SensorLocalInfo>
    ) : SensorsUiState {
        if (info.isSuccess) {
            return SensorsUiState(sensors = uiMapper.mapSensorInfoToUi(info.getOrDefault(listOf()), localInfo))
        } else {
            return SensorsUiState(connectionError = true, errorMessage = info.exceptionOrNull()?.localizedMessage ?: "")
        }
    }

    fun onSensorClick(sensorInfo: SensorUiInfo) {
        _uiState.value = _uiState.value.copy(
            selectedSensorInfo = sensorInfo,
            showEditor = true
        )
    }

    fun onCloseEditor() {
        _uiState.value = _uiState.value.copy(
            showEditor = false
        )
    }

    fun onColorChanged(color: Color) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                setSensorLocalInfoUseCase(
                    uiMapper.mapSensorLocalInfoFromUi(_uiState.value.selectedSensorInfo.copy(color = color))
                )
            }
        }
    }

    fun onEnableChecked(enabled: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                setSensorLocalInfoUseCase(
                    uiMapper.mapSensorLocalInfoFromUi(_uiState.value.selectedSensorInfo.copy(enabled = enabled))
                )
                _uiState.value = _uiState.value.copy(
                    selectedSensorInfo = _uiState.value.selectedSensorInfo.copy(enabled = enabled)
                )
            }
        }
    }

    fun onDescriptionChanged(description: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                setSensorLocalInfoUseCase(
                    uiMapper.mapSensorLocalInfoFromUi(_uiState.value.selectedSensorInfo.copy(description = description))
                )
                _uiState.value = _uiState.value.copy(
                    selectedSensorInfo = _uiState.value.selectedSensorInfo.copy(description = description)
                )
            }
        }
    }

    fun onMultiplierChanged(multiplier: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                setSensorLocalInfoUseCase(
                    uiMapper.mapSensorLocalInfoFromUi(_uiState.value.selectedSensorInfo.copy(multiplier = multiplier))
                )
                _uiState.value = _uiState.value.copy(
                    selectedSensorInfo = _uiState.value.selectedSensorInfo.copy(multiplier = multiplier)
                )
            }
        }
    }
}
