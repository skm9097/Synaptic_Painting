package com.synaptic.painting.ai

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import com.synaptic.painting.model.Stroke
import kotlin.math.cos
import kotlin.math.sin

/**
 * Deterministic placeholder agent. Emits a small spiral of strokes plus a
 * tinted bitmap so the AI button has a visible effect before any real model
 * is wired in.
 */
class StubDrawingAgent : DrawingAgent {

    override suspend fun draw(context: AgentContext): AgentOutput {
        val center = Offset(
            x = (context.canvasSize.width.takeIf { it > 0f } ?: 400f) / 2f,
            y = (context.canvasSize.height.takeIf { it > 0f } ?: 600f) / 2f,
        )
        return AgentOutput.Mixed(
            listOf(
                AgentOutput.Strokes(listOf(spiral(center))),
                AgentOutput.Bitmap(swatch(), center + Offset(40f, -80f)),
            )
        )
    }

    private fun spiral(center: Offset): Stroke {
        val points = ArrayList<Offset>(120)
        var angle = 0f
        var radius = 4f
        repeat(120) {
            points += Offset(center.x + radius * cos(angle), center.y + radius * sin(angle))
            angle += 0.25f
            radius += 0.6f
        }
        return Stroke(points = points, color = Color(0xFF6C5CE7), widthPx = 5f)
    }

    private fun swatch(): androidx.compose.ui.graphics.ImageBitmap {
        val bmp = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
        val canvas = AndroidCanvas(bmp)
        val paint = Paint().apply {
            color = android.graphics.Color.argb(160, 255, 209, 102)
            isAntiAlias = true
        }
        canvas.drawCircle(32f, 32f, 30f, paint)
        return bmp.asImageBitmap()
    }
}
