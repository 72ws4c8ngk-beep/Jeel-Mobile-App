package edu.iau.cshj.csc402.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R

internal val JeelSans=FontFamily(Font(R.font.inter,FontWeight.Normal),Font(R.font.inter,FontWeight.Medium),Font(R.font.inter,FontWeight.SemiBold),Font(R.font.inter,FontWeight.Bold))
internal val JeelSerif=FontFamily(Font(R.font.lora,FontWeight.Normal),Font(R.font.lora,FontWeight.SemiBold))
private fun type(size:Int,line:Int,weight:FontWeight=FontWeight.Normal)=TextStyle(fontFamily=JeelSans,fontSize=size.sp,lineHeight=line.sp,fontWeight=weight,letterSpacing=0.sp)
val Typography=Typography(
    displayLarge=type(57,64),displayMedium=type(45,52),displaySmall=type(36,44),
    headlineLarge=type(32,40),headlineMedium=type(28,36),headlineSmall=type(24,32),
    titleLarge=type(22,30),titleMedium=type(16,24,FontWeight.SemiBold),titleSmall=type(14,20,FontWeight.SemiBold),
    bodyLarge=type(16,24),bodyMedium=type(14,21),bodySmall=type(12,18),
    labelLarge=type(14,20,FontWeight.SemiBold),labelMedium=type(12,18,FontWeight.SemiBold),labelSmall=type(11,15)
)
