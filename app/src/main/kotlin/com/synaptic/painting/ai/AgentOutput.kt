package com.synaptic.painting.ai

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import com.synaptic.painting.model.Stroke

sealed interface AgentOutput {
    data class Strokes(val strokes: List<Stroke>) : AgentOutput
    data class Bitmap(val image: ImageBitmap, val topLeft: Offset) : AgentOutput
    data class Mixed(val parts: List<AgentOutput>) : AgentOutput
}
