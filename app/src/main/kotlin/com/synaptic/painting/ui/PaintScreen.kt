package com.synaptic.painting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synaptic.painting.model.DrawingViewModel

@Composable
fun PaintScreen(viewModel: DrawingViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            ToolBar(
                state = state,
                onColorChange = viewModel::setColor,
                onWidthChange = viewModel::setWidth,
                onAiDraw = { viewModel.requestAiDraw(canvasSize) },
            )
            PaintCanvas(
                state = state,
                onSizeChange = { canvasSize = it },
                onBeginStroke = viewModel::beginStroke,
                onAppendPoint = viewModel::appendPoint,
                onEndStroke = viewModel::endStroke,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
