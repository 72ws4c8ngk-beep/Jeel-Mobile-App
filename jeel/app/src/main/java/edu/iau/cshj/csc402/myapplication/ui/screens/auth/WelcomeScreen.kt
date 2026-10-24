package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.WelcomeScreen(state:AuthUiState) {
    with(state){
        Brand()
        Spacer(Modifier.height(24.dp))
        Box(Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(24.dp))) {
            Image(painterResource(R.drawable.jeel_mentorship), "A mentor and mentee talking in a library", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Surface(Modifier.align(Alignment.BottomStart).padding(14.dp), shape = RoundedCornerShape(12.dp), color = Color.White) {
                Text("One conversation. A new perspective.", Modifier.padding(10.dp), color = Navy, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Your next chapter\nstarts with a mentor.", color = Navy, fontFamily = FontFamily.Serif, fontSize = 32.sp, lineHeight = 36.sp)
        Spacer(Modifier.height(14.dp))
        Body("Connect with experienced professionals and retired experts. Build confidence, find direction, and turn your ambitions into a plan.")
        Spacer(Modifier.height(24.dp))
        Action("→  Get started") {
            go("signup")
        }
        Spacer(Modifier.height(10.dp))
        Action("Login", secondary = true) {
            go("login")
        }
        Footer("Real experience. Meaningful guidance. Your pace.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun WelcomeScreenPreview() {
    PreviewScreen { WelcomeScreen(AuthUiState()) }
}
