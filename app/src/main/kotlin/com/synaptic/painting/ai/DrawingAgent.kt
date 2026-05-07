package com.synaptic.painting.ai

import androidx.compose.ui.geometry.Size
import com.synaptic.painting.model.Stroke

data class AgentContext(
    val canvasSize: Size,
    val existingStrokes: List<Stroke>,
)

interface DrawingAgent {
    suspend fun draw(context: AgentContext): AgentOutput
}
