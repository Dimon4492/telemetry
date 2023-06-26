package com.lexx.presentation.ui.sensors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.domain.models.SensorInfo
import com.lexx.presentation.R

@Composable
fun SensorsPage (
    sensorsViewModel: SensorsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = sensorsViewModel.uiState.collectAsState().value
    LazyColumn(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)) {
        if (uiState.connectionError) {
            item {
                Row(Modifier
                    .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.server_connect_error),
                        color = Color.Red,
                        modifier = Modifier.weight(1.0f)
                    )
                }
            }
        } else {
            items(
                items = uiState.sensors,
                key = { it.nameId }
            ) { sensorInfo ->
                SensorCard(sensorInfo, Modifier)
            }
        }
    }
}
@Composable
fun SensorCard(sensorInfo: SensorInfo, modifier: Modifier.Companion) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = sensorInfo.name,
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
