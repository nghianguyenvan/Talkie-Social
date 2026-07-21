package com.example.talkiesocial.core.ui.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.neonGlow(
    color: Color,
    borderRadius: Dp = 12.dp,
    blurRadius: Dp = 15.dp,
    spread: Dp = 2.dp
) = this.drawBehind {
    val paint = Paint().apply {
        style = PaintingStyle.Fill
        asFrameworkPaint().apply {
            this.color = Color.Transparent.toArgb()
            setShadowLayer(
                blurRadius.toPx(),
                0f,
                0f,
                color.toArgb()
            )
        }
    }

    drawIntoCanvas { canvas ->
        canvas.drawRoundRect(
            left = spread.toPx(),
            top = spread.toPx(),
            right = size.width - spread.toPx(),
            bottom = size.height - spread.toPx(),
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint = paint
        )
    }
}
