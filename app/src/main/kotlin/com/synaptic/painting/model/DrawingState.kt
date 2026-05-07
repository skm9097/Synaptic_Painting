package com.synaptic.painting.model

import androidx.compose.ui.graphics.Color

data class DrawingState(
    val strokes: List<Stroke> = emptyList(),
    val overlays: List<BitmapLayer> = emptyList(),
    val inProgress: Stroke? = null,
    val currentColor: Color = Color.Black,
    val currentWidthPx: Float = 8f,
    val isAiBusy: Boolean = false,
)
