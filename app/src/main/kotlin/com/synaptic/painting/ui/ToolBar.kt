package com.synaptic.painting.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.synaptic.painting.R
import com.synaptic.painting.model.DrawingState

@Composable
fun ToolBar(
    state: DrawingState,
    onColorChange: (Color) -> Unit,
    onWidthChange: (Float) -> Unit,
    onAiDraw: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        ColorPicker(selected = state.currentColor, onSelect = onColorChange)
        BrushSizeSlider(
            widthPx = state.currentWidthPx,
            onChange = onWidthChange,
            modifier = Modifier.padding(top = 8.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (state.isAiBusy) {
                CircularProgressIndicator(modifier = Modifier.padding(end = 12.dp))
            }
            Button(onClick = onAiDraw, enabled = !state.isAiBusy) {
                Text(stringResource(R.string.ai_draw))
            }
        }
    }
}
