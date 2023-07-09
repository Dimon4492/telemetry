package com.lexx.presentation.ui.sensors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.R
import com.lexx.presentation.models.SensorUiInfo

@Composable
fun SensorsPage (
    modifier: Modifier = Modifier,
    sensorsViewModel: SensorsViewModel = viewModel(),
) {
    val uiState = sensorsViewModel.uiState.collectAsState().value
    if (uiState.showEditor) {
        SensorEditor(
            modifier = modifier,
            currentSensorInfo = uiState.selectedSensorInfo,
            onClose = {sensorsViewModel.onCloseEditor()},
            onColorChanged = {sensorsViewModel.onColorChanged(it)},
            onCheckedChange = { sensorsViewModel.onEnableChecked(it)},
            onDescriptionChanged = { sensorsViewModel.onDescriptionChanged(it)},
            onMultiplierChanged = { sensorsViewModel.onMultiplierChanged(it)}
        )
    } else if(uiState.noSensorsError) {
        Text(
            text = stringResource(id = R.string.no_sensors_error),
            modifier.fillMaxSize()
        )
    } else if(uiState.connectionError) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.server_connect_error),
                    modifier = Modifier.wrapContentHeight()
                )
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                )
            }
    } else {
        LazyColumn(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            items(
                items = uiState.sensors,
                key = { it.nameId }
            ) { sensorInfo ->
                SensorCard(
                    enabled = sensorInfo.enabled,
                    sensorInfo = sensorInfo,
                    onClick = {
                        sensorsViewModel.onSensorClick(it)
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun SensorCard(
    enabled: Boolean,
    sensorInfo: SensorUiInfo,
    onClick: (sensorInfo: SensorUiInfo) -> Unit,
    modifier: Modifier.Companion
) {
    Button(
        onClick = { onClick(sensorInfo) },
        modifier = modifier
            .fillMaxWidth()
    ) {
        val color = if (enabled) {
            sensorInfo.color
        } else {
            Color.Gray
        }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            colors = CardDefaults.cardColors(containerColor=color)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = sensorInfo.remoteName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = sensorInfo.description,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row {
                    Text(stringResource(id = R.string.last_value), Modifier.wrapContentWidth())
                    Text(sensorInfo.lastValue)
                }

                Row {
                    Text(stringResource(id = R.string.last_seen), Modifier.wrapContentWidth())
                    Text(sensorInfo.lastTimestamp)
                }
            }
        }
    }
}
