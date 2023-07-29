package com.lexx.presentation.ui

import androidx.lifecycle.ViewModel
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.sensors.SensorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val sensorsRepository: SensorsRepository,
    private val plotRepository: PlotRepository,
) : ViewModel() {
    fun onPause() {
        sensorsRepository.pauseNetworkPolling(true)
        plotRepository.pauseNetworkPolling(true)
    }

    fun onResume() {
        sensorsRepository.pauseNetworkPolling(false)
        plotRepository.pauseNetworkPolling(false)
    }
}
