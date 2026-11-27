cat << 'EOF' > GameTapService.kt
package com.example.agentapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent

class GameTapService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var completedGames = 0
    private var targetGames = 100_000
    private var shotStep = 0

    private val targetPackageName = "com.example.bubbleshooter"

    var latestScreenBitmap: Bitmap? = null

    private val shooterX = 540f
    private val shooterY = 1720f

    private val aimTargets = listOf(
        Pair(260f, 620f),
        Pair(420f, 580f),
        Pair(540f, 560f),
        Pair(680f, 580f),
        Pair(820f, 620f)
    )

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {
        stopAutomation()
    }

    fun tapAt(x: Float, y: Float, durationMs: Long = 70L) {
        val path = Path().apply { moveTo(x, y) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0L, durationMs))
            .build()
        dispatchGesture(gesture, null, null)
    }

    fun aimAndShoot(targetX: Float, targetY: Float, durationMs: Long = 180L) {
        val path = Path().apply {
            moveTo(shooterX, shooterY)
            lineTo(targetX, targetY)
        }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0L, durationMs))
            .build()
        dispatchGesture(gesture, null, null)
    }

    fun stopAutomation() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }
}
EOF
