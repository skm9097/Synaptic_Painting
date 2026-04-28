package com.synaptic.painting.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap

data class BitmapLayer(
    val image: ImageBitmap,
    val topLeft: Offset,
)
