package com.lexx.domain.features.plot

import com.lexx.domain.models.PlotInfo
import javax.inject.Inject

class GetPlotInfoUseCase @Inject constructor(
    private val plotRepository: PlotRepository,
){
    suspend operator fun invoke(): PlotInfo {
        return plotRepository.getPlotInfo()
    }
}
