package com.lexx.presentation.ui.plot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.plot.GetPlotInfoUseCase
import com.lexx.presentation.models.PlotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlotViewModel @Inject constructor(
    val getPlotInfoUseCase: GetPlotInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlotUiState())
    val uiState: StateFlow<PlotUiState> = _uiState.asStateFlow()

    init {
        loadSensors()
    }

    private fun loadSensors() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                plotInfo = getPlotInfoUseCase()
            )
        }
    }
}
