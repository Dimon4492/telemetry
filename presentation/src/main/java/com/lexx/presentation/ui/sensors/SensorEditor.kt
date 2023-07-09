package com.lexx.presentation.ui.sensors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.lexx.presentation.R
import com.lexx.presentation.models.SensorUiInfo

@Composable
fun SensorEditor(
    modifier: Modifier,
    currentSensorInfo: SensorUiInfo,
    onClose: () -> Unit,
    onColorChanged: (Color) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onMultiplierChanged: (String) -> Unit,
) {
    val controller = rememberColorPickerController()

    Column(
        // on below line we are adding a modifier to it,
        modifier = modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.sensor_name_label) + currentSensorInfo.remoteName,
            )

            Text(
                text = stringResource(id = R.string.enabled_sensor_label),
                modifier = Modifier.padding(start = 20.dp)
            )

            Checkbox(
                checked = currentSensorInfo.enabled,
                onCheckedChange = onCheckedChange,
            )

            Text(
                text = stringResource(id = R.string.sensor_multiplier_label),
                modifier = Modifier.padding(start = 20.dp)
            )
            TextField(
                value = currentSensorInfo.multiplier,
                onValueChange = {
                    onMultiplierChanged(it)
                }
            )
        }

        TextField(
            value = currentSensorInfo.description,
            onValueChange = {onDescriptionChanged(it)}
        )

        Button(
            onClick = {
                onColorChanged(controller.selectedColor.value)
                onClose()
            }
        ) {
            Text(
                text = stringResource(id = R.string.close)
            )
        }

        // on below line we are adding a row.
        Row(
            // on below line we are adding a modifier
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            // on below line we are adding horizontal
            // and vertical alignment.
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // on below line we are adding a alpha tile.
            AlphaTile(
                // on below line we are
                // adding modifier to it
                modifier = Modifier
                    .fillMaxWidth()
                    // on below line
                    // we are adding a height.
                    .height(60.dp)
                    // on below line we are adding clip.
                    .clip(RoundedCornerShape(6.dp)),
                // on below line we are adding controller.
                controller = controller,
            )
        }
        // on below line we are
        // adding horizontal color picker.
        HsvColorPicker(
            // on below line we are
            // adding a modifier to it
            modifier = Modifier
                .weight(3F)
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            // on below line we are
            // adding a controller
            controller = controller,
            // on below line we are
            // adding on color changed.
            onColorChanged = {
                onColorChanged(it.color)
            },
            initialColor = currentSensorInfo.color
        )
        // on below line we are adding a alpha slider.
        AlphaSlider(
            // on below line we
            // are adding a modifier to it.
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            // on below line we are
            // adding a controller.
            controller = controller,
            // on below line we are
            // adding odd and even color.
            tileOddColor = Color.White,
            tileEvenColor = Color.Black,
        )
        // on below line we are
        // adding a brightness slider.
        BrightnessSlider(
            // on below line we
            // are adding a modifier to it.
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            // on below line we are
            // adding a controller.
            controller = controller,
        )
    }
}
