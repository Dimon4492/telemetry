package com.lexx.presentation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel(),
) {
    val settingsUiState by settingsViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        ServerAddressSettings(settingsUiState.serverAddress, {serverAddress ->
            settingsViewModel.setServerAddress(serverAddress)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerAddressSettings(
    serverAddress: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Server address: ",
            modifier = modifier
        )
        TextField(
            value = serverAddress,
            onValueChange = onValueChange
        )
    }
}

@Preview
@Composable
fun ServerAddressSettingsPreview() {
    ServerAddressSettings("127.0.0.1:80", {})
}
