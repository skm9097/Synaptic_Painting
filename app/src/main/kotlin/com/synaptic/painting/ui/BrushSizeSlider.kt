package com.synaptic.painting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.synaptic.painting.R

@Composable
fun BrushSizeSlider(
    widthPx: Float,
    onChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "${stringResource(R.string.brush_size)}: ${widthPx.toInt()} px")
        Slider(
            value = widthPx,
            onValueChange = onChange,
            valueRange = 1f..64f,
        )
    }
}
