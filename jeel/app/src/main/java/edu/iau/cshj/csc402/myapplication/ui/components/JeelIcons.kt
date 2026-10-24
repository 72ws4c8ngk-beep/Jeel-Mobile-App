package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun JeelOutlineIcon(name:String) {
    Canvas(Modifier.size(23.dp).semantics {contentDescription=name.replaceFirstChar{it.uppercase()}}) {
        val ink=Color(0xFF52636B)
        scale(size.width/24f,size.height/24f,pivot=Offset.Zero) {
            val pen=Stroke(width=1.5f,cap=StrokeCap.Round)
            val p=Path()
            when(name) {
                "home"->{p.moveTo(3f,10f);p.lineTo(12f,2f);p.lineTo(21f,10f);p.lineTo(21f,22f);p.lineTo(15f,22f);p.lineTo(15f,15f);p.lineTo(9f,15f);p.lineTo(9f,22f);p.lineTo(3f,22f);p.close()}
                "explore"->{drawCircle(ink,9f,Offset(12f,12f),style=pen);p.moveTo(16f,8f);p.lineTo(13f,14f);p.lineTo(8f,16f);p.lineTo(11f,10f);p.close()}
                "sessions"->{p.moveTo(3f,5f);p.lineTo(21f,5f);p.lineTo(21f,22f);p.lineTo(3f,22f);p.close();p.moveTo(3f,10f);p.lineTo(21f,10f);p.moveTo(7f,2f);p.lineTo(7f,7f);p.moveTo(17f,2f);p.lineTo(17f,7f);for(x in listOf(7f,12f,17f))for(y in listOf(14f,18f))drawCircle(ink,.7f,Offset(x,y))}
                "progress"->{p.moveTo(3f,21f);p.lineTo(3f,14f);p.moveTo(8f,21f);p.lineTo(8f,12f);p.moveTo(13f,21f);p.lineTo(13f,16f);p.moveTo(18f,21f);p.lineTo(18f,10f);p.moveTo(3f,10f);p.lineTo(9f,5f);p.lineTo(14f,9f);p.lineTo(21f,2f);p.moveTo(16f,2f);p.lineTo(21f,2f);p.lineTo(21f,7f)}
                "profile"->{drawCircle(ink,4f,Offset(12f,6f),style=pen);p.moveTo(4f,22f);p.cubicTo(4f,10f,20f,10f,20f,22f)}
                "requests"->{p.moveTo(3f,8f);p.lineTo(21f,8f);p.lineTo(21f,21f);p.lineTo(3f,21f);p.close();p.moveTo(8f,8f);p.lineTo(8f,4f);p.lineTo(16f,4f);p.lineTo(16f,8f);p.moveTo(3f,12f);p.lineTo(9f,15f);p.lineTo(15f,15f);p.lineTo(21f,12f)}
                else->{p.moveTo(4f,18f);p.lineTo(6f,15f);p.lineTo(6f,9f);p.cubicTo(6f,1f,18f,1f,18f,9f);p.lineTo(18f,15f);p.lineTo(20f,18f);p.close();p.moveTo(10f,21f);p.cubicTo(11f,23f,13f,23f,14f,21f)}
            }
            drawPath(p,ink,style=pen)
        }
    }
}
