package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
internal fun JeelLogoIcon(modifier:Modifier=Modifier,color:Color=DarkNavyBackground){
    Canvas(modifier = modifier) {
        val strokeWidth = 4.5.dp.toPx()
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.18f, h * 0.72f)
            lineTo(w * 0.18f, h * 0.44f)
            cubicTo(
            w * 0.18f, h * 0.26f,
            w * 0.38f, h * 0.26f,
            w * 0.50f, h * 0.44f
            )
            cubicTo(
            w * 0.62f, h * 0.26f,
            w * 0.82f, h * 0.26f,
            w * 0.82f, h * 0.44f
            )
            lineTo(w * 0.82f, h * 0.72f)
        }
        drawPath(
        path = path,
        color = color,
        style = Stroke(
        width = strokeWidth,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
        )
        )
    }
}
