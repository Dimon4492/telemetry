package com.lexx.domain.features.plot

import com.lexx.domain.models.PlotInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSixHoursPlotInfoUseCase @Inject constructor(
    private val plotRepository: PlotRepository,
){
    suspend operator fun invoke(): Flow<Result<PlotInfo>> {
        return plotRepository.getSixHoursPlotInfo()
    }
}
