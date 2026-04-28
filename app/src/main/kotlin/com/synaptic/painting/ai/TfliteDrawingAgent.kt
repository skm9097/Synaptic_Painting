package com.synaptic.painting.ai

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

/**
 * Scaffolding for an on-device Transformer drawing model.
 *
 * No model file ships with this build. To enable:
 *   1. Place a model at app/src/main/assets/{modelAsset}.
 *   2. Implement encodeInput(...) and decodeOutput(...) for that model's
 *      tensor contract.
 *   3. Swap DrawingViewModel's default agent to this class.
 *
 * Until then, draw() falls back to the stub so the UI keeps working.
 */
class TfliteDrawingAgent(
    appContext: Context,
    private val modelAsset: String = "drawing_transformer.tflite",
    private val fallback: DrawingAgent = StubDrawingAgent(),
) : DrawingAgent {

    private val context = appContext.applicationContext
    private val interpreter: Interpreter? = runCatching { loadInterpreter() }
        .onFailure { Log.w(TAG, "Falling back to stub: ${it.message}") }
        .getOrNull()

    override suspend fun draw(context: AgentContext): AgentOutput {
        val tflite = interpreter ?: return fallback.draw(context)
        // TODO: define real I/O tensors for the chosen model.
        //   val input  = encodeInput(context)
        //   val output = allocateOutput()
        //   tflite.run(input, output)
        //   return decodeOutput(output, context)
        Log.i(TAG, "TFLite interpreter loaded but no I/O contract implemented; using stub.")
        return fallback.draw(context)
    }

    private fun loadInterpreter(): Interpreter {
        val buffer = loadAsset(modelAsset)
        return Interpreter(buffer)
    }

    private fun loadAsset(name: String): ByteBuffer {
        val fd = context.assets.openFd(name)
        FileInputStream(fd.fileDescriptor).use { input ->
            return input.channel.map(FileChannel.MapMode.READ_ONLY, fd.startOffset, fd.declaredLength)
        }
    }

    private companion object {
        const val TAG = "TfliteDrawingAgent"
    }
}
