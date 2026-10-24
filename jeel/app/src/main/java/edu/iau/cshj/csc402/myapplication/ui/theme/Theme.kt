package edu.iau.cshj.csc402.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun JeelTheme(content:@Composable ()->Unit){
    MaterialTheme(
    colorScheme=lightColorScheme(primary=Navy,secondary=Gold,background=Cream,surface=Color.White,onSurface=Navy,onBackground=Navy,onPrimary=Color.White),
    typography=Typography,
    content=content
    )
}
