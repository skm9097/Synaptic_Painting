package com.synaptic.painting.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaptic.painting.ai.AgentContext
import com.synaptic.painting.ai.AgentOutput
import com.synaptic.painting.ai.DrawingAgent
import com.synaptic.painting.ai.StubDrawingAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrawingViewModel(
    private val agent: DrawingAgent = StubDrawingAgent(),
) : ViewModel() {

    private val _state = MutableStateFlow(DrawingState())
    val state: StateFlow<DrawingState> = _state.asStateFlow()

    fun setColor(color: Color) = _state.update { it.copy(currentColor = color) }

    fun setWidth(widthPx: Float) = _state.update { it.copy(currentWidthPx = widthPx) }

    fun beginStroke(at: Offset) = _state.update {
        it.copy(inProgress = Stroke(listOf(at), it.currentColor, it.currentWidthPx))
    }

    fun appendPoint(at: Offset) = _state.update { current ->
        val active = current.inProgress ?: return@update current
        current.copy(inProgress = active.copy(points = active.points + at))
    }

    fun endStroke() = _state.update { current ->
        val active = current.inProgress ?: return@update current
        current.copy(strokes = current.strokes + active, inProgress = null)
    }

    fun requestAiDraw(canvasSize: Size) {
        if (_state.value.isAiBusy) return
        _state.update { it.copy(isAiBusy = true) }
        viewModelScope.launch {
            val output = withContext(Dispatchers.Default) {
                agent.draw(AgentContext(canvasSize, _state.value.strokes))
            }
            _state.update { current -> applyOutput(current, output).copy(isAiBusy = false) }
        }
    }

    private fun applyOutput(state: DrawingState, output: AgentOutput): DrawingState =
        when (output) {
            is AgentOutput.Strokes -> state.copy(strokes = state.strokes + output.strokes)
            is AgentOutput.Bitmap -> state.copy(overlays = state.overlays + BitmapLayer(output.image, output.topLeft))
            is AgentOutput.Mixed -> output.parts.fold(state, ::applyOutput)
        }
}
