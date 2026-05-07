package com.synaptic.painting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val palette = listOf(
    Color.Black,
    Color(0xFFE63946), // red
    Color(0xFFF4A261), // orange
    Color(0xFFFFD166), // yellow
    Color(0xFF2A9D8F), // teal
    Color(0xFF264653), // navy
    Color(0xFF6C5CE7), // purple
    Color.White,
)

@Composable
fun ColorPicker(
    selected: Color,
    onSelect: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        palette.forEach { color ->
            val borderColor = if (color == selected) Color(0xFF333333) else Color(0x33000000)
            val borderWidth = if (color == selected) 3.dp else 1.dp
            Spacer(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(borderWidth, borderColor, CircleShape)
                    .clickable { onSelect(color) }
            )
        }
    }
}
