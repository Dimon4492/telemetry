package com.lexx.telemetry.ui.plot

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.R
import com.lexx.presentation.models.PlotUiInfo
import com.lexx.presentation.models.PlotUiPageState
import com.lexx.presentation.ui.plot.PlotViewModel

@Composable
fun HourPlotPage (
    modifier: Modifier = Modifier,
    plotViewModel: PlotViewModel = viewModel(),
) {
    val uiState = plotViewModel.uiState.collectAsState().value
    PlotPageBase(modifier, uiState.hourPlotUiPageState)
}

@Composable
fun SixHoursPlotPage (
    modifier: Modifier = Modifier,
    plotViewModel: PlotViewModel = viewModel(),
) {
    val uiState = plotViewModel.uiState.collectAsState().value
    PlotPageBase(modifier, uiState.sixHoursPlotUiPageState)
}
@Composable
fun DayPlotPage (
    modifier: Modifier = Modifier,
    plotViewModel: PlotViewModel = viewModel(),
) {
    val uiState = plotViewModel.uiState.collectAsState().value
    PlotPageBase(modifier, uiState.dayPlotUiPageState)
}

@Composable
fun PlotPageBase (
    modifier: Modifier = Modifier,
    plotUiPageState: PlotUiPageState,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (plotUiPageState.noDataError) {
            Column {
                Text(
                    text = stringResource(id = R.string.no_data_error),
                    Modifier.wrapContentHeight(),
                )
                Text(
                    text = plotUiPageState.errorMessage,
                    Modifier.wrapContentHeight(),
                    color = Color.Red,
                )
            }
        } else if (plotUiPageState.connectionError) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.server_connect_error),
                        modifier = Modifier.wrapContentHeight(),
                    )
                    Text(
                        text = plotUiPageState.errorMessage,
                        modifier = Modifier.wrapContentHeight(),
                        color = Color.Red,
                    )
                }
            }
        } else {
            TelemetryPlot(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                xValues = plotUiPageState.xValues,
                yValues = plotUiPageState.yValues,
                plotInfo = plotUiPageState.plotInfo,
                paddingSpace = dimensionResource(id = R.dimen.plot_padding_space),
            )
        }
    }
}

@Composable
fun TelemetryPlot(
    modifier : Modifier,
    xValues: List<String>,
    yValues: List<String>,
    plotInfo: PlotUiInfo,
    paddingSpace: Dp,
) {
    val canvasLeftPadding = dimensionResource(id = R.dimen.plot_left_padding)
    val canvasRightPadding = dimensionResource(id = R.dimen.plot_right_padding)
    val canvasBottomPadding = dimensionResource(id = R.dimen.plot_bottom_padding)
    val canvasTopPadding = dimensionResource(id = R.dimen.plot_top_padding)

    val yTextWidth = dimensionResource(id = R.dimen.plot_y_text_width)
    val xTextWidth = dimensionResource(id = R.dimen.plot_x_text_width)
    val xTextHeight = dimensionResource(id = R.dimen.plot_x_text_height)

    val labelSize = dimensionResource(id = R.dimen.plot_text_size)

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { labelSize.toPx() }
        }
    }

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(
                horizontal = dimensionResource(id = R.dimen.canvas_horizontal_padding),
                vertical = dimensionResource(id = R.dimen.canvas_vertical_padding)
            ),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.Black)
        ) {
            if (xValues.size > 0) {
                val canvasSpaceWidth = size.width - canvasLeftPadding.toPx() - paddingSpace.toPx() - yTextWidth.toPx() - canvasRightPadding.toPx()
                val canvasSpaceHeight = size.height - canvasBottomPadding.toPx() - paddingSpace.toPx() - canvasTopPadding.toPx()

                val xAxisSpace = canvasSpaceWidth / (xValues.size - 1)
                val yAxisSpace = canvasSpaceHeight / yValues.size

                // x axis
                for (i in xValues.indices) {
                    val x = xAxisSpace * i + canvasLeftPadding.toPx() + yTextWidth.toPx() - xTextWidth.toPx()/2
                    val y = canvasSpaceHeight + canvasTopPadding.toPx() + xTextHeight.toPx()/2
                    with(drawContext.canvas) {
                        save()
                        nativeCanvas.rotate(330F, x, y )
                        nativeCanvas.drawText(xValues[i], x, y, textPaint)
                        restore()
                    }
                }

                // y axis
                for (i in yValues.indices) {
                    val x = paddingSpace.toPx()/2 + canvasLeftPadding.toPx() + yTextWidth.toPx() / 2
                    val y = canvasSpaceHeight - yAxisSpace * (i + 1) + canvasBottomPadding.toPx() + labelSize.toPx()
                    drawContext.canvas.nativeCanvas.drawText(yValues[i], x, y, textPaint)
                }

                if (plotInfo.values.isNotEmpty()) {
                    for (plotLine in plotInfo.values) {
                        if (!plotLine.enabled) {
                            continue
                        }

                        val minTimestamp = plotInfo.minTimestamp
                        val timestampRange = plotInfo.maxTimestamp - minTimestamp
                        val minValue = plotInfo.minValue
                        val valueRange = plotInfo.maxValue - minValue

                        val plotPoints = plotLine.values
                        val coordinates2 = mutableListOf<PointF>()
                        for (i in plotLine.values.indices) {
                            val x2 =
                                canvasSpaceWidth * (plotPoints[i].timestamp - minTimestamp).toFloat() / timestampRange.toFloat()
                            val y2 =
                                canvasSpaceHeight * (1 - (plotPoints[i].value - minValue) / valueRange)
                            coordinates2.add(PointF(x2 + canvasLeftPadding.toPx() + yTextWidth.toPx(), y2 + canvasBottomPadding.toPx()))
                        }

                        if (coordinates2.isNotEmpty()) {
                            val stroke = Path().apply {
                                reset()
                                moveTo(coordinates2.first().x, coordinates2.first().y)
                                for (i in 0 until coordinates2.size - 1) {
                                    lineTo(coordinates2[i + 1].x, coordinates2[i + 1].y)
                                }
                            }

                            drawPath(
                                stroke,
                                color = plotLine.color,
                                style = Stroke(
                                    width = 3f
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
