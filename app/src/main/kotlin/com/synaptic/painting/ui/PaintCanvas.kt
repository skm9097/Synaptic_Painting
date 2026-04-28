package com.synaptic.painting.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke as DrawStroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.unit.IntSize
import com.synaptic.painting.model.DrawingState
import com.synaptic.painting.model.Stroke

@Composable
fun PaintCanvas(
    state: DrawingState,
    onSizeChange: (Size) -> Unit,
    onBeginStroke: (Offset) -> Unit,
    onAppendPoint: (Offset) -> Unit,
    onEndStroke: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .background(Color.White)
            .onSizeChanged { size: IntSize ->
                onSizeChange(Size(size.width.toFloat(), size.height.toFloat()))
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset -> onBeginStroke(offset) },
                    onDrag = { change, _ ->
                        change.consume()
                        onAppendPoint(change.position)
                    },
                    onDragEnd = { onEndStroke() },
                    onDragCancel = { onEndStroke() },
                )
            },
    ) {
        state.overlays.forEach { layer ->
            drawImage(image = layer.image, topLeft = layer.topLeft)
        }
        state.strokes.forEach { drawStroke(it) }
        state.inProgress?.let { drawStroke(it) }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStroke(stroke: Stroke) {
    if (stroke.points.isEmpty()) return
    drawPath(
        path = stroke.toPath(),
        color = stroke.color,
        style = DrawStroke(width = stroke.widthPx, cap = StrokeCap.Round),
    )
}
