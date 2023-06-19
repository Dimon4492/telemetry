package com.lexx.telemetry.ui.plot

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.domain.models.PlotInfo
import com.lexx.presentation.ui.plot.PlotViewModel

@Composable
fun PlotPage (
    plotViewModel: PlotViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = plotViewModel.uiState.collectAsState().value
    val yStep = 50
    val points = listOf(150f,100f,250f,200f,330f,300f,90f,120f,285f,199f)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        TelemetryPlot(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            xValues = (0..9).map { it + 1 },
            yValues = (0..6).map { (it + 1) * yStep },
            points = points,
            plotInfo = uiState.plotInfo,
            paddingSpace = 16.dp,
            verticalStep = yStep
        )
    }
}

@Composable
fun TelemetryPlot(
    modifier : Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    plotInfo: PlotInfo,
    paddingSpace: Dp,
    verticalStep: Int
) {
    val coordinates = mutableListOf<PointF>()

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size
            /** placing x axis points */
            for (i in xValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${xValues[i]}",
                    xAxisSpace * (i + 1),
                    size.height - 30,
                    textPaint
                )
            }

            for (i in yValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${yValues[i]}",
                    paddingSpace.toPx() / 2f,
                    size.height - yAxisSpace * (i + 1),
                    textPaint
                )
            }

            for (i in points.indices) {
                val x1 = xAxisSpace * xValues[i]
                val y1 = size.height - (yAxisSpace * (points[i]/verticalStep.toFloat()))
                coordinates.add(PointF(x1,y1))
            }

            if (plotInfo.values.isNotEmpty()) {
                for (plotLine in plotInfo.values) {
                    val minTimestamp = plotInfo.minTimestamp
                    val timestampRange = plotInfo.maxTimestamp - minTimestamp
                    val minValue = plotInfo.minValue
                    val valueRange = plotInfo.maxValue - minValue

                    val plotPoints = plotLine.values
                    val coordinates2 = mutableListOf<PointF>()
                    for (i in plotLine.values.indices) {
                        val x2 =
                            size.width * (plotPoints[i].timestamp - minTimestamp).toFloat() / timestampRange.toFloat()
                        val y2 = size.height * (plotPoints[i].value - minValue) / valueRange
                        coordinates2.add(PointF(x2, y2))
                    }

                    if (coordinates2.isNotEmpty()) {
                        val stroke = Path().apply {
                            reset()
                            moveTo(coordinates2.first().x, coordinates2.first().y)
                            for (i in 0 until coordinates2.size - 1) {
                                lineTo(coordinates2[i + 1].x,coordinates2[i + 1].y)
                            }
                        }

                        drawPath(
                            stroke,
                            color = Color.Black,
                            style = Stroke(
                                width = 1f
                            )
                        )
                    }
                }
            }
        }
    }
}
