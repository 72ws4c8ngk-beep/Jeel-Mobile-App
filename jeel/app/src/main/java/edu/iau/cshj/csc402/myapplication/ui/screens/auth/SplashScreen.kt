package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun SplashScreen(){
    Box(
    modifier = Modifier
    .fillMaxSize()
    .background(DarkNavyBackground)
    .statusBarsPadding()
    .padding(horizontal = 24.dp)
    ) {
        // Top Subtitle Header
        Text(
        text = "MENTORSHIP, WITH PURPOSE",
        color = HeaderTextGray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 2.sp,
        modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 24.dp)
        )
        Column(
        modifier = Modifier.align(Alignment.BottomCenter)
        .navigationBarsPadding().padding(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
            modifier = Modifier.width(72.dp).height(3.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Color(0xFF385566))
            ) {
                Box(Modifier.fillMaxWidth(0.4f).height(3.dp)
                .background(Color(0xFFB98B62)))
            }
            Spacer(Modifier.height(18.dp))
            Text(
            text = "Preparing your next chapter",
            color = SubtitleGray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
            )
        }
        // Center Content
        Column(
        modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
            // Brand Logo & Title Row
            Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
                // White rounded square with custom M icon
                Surface(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color.White
                ) {
                    Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                    ) {
                        JeelLogoIcon(modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Jeel | جيل Title
                Text(
                text = "Jeel",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
                )
                Text(
                text = " | ",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                text = "جيل",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(44.dp))
            // Main Headline
            Text(
            text = "Experience shared.\nPotential unlocked.",
            color = Color.White,
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 42.sp
            )
            Spacer(modifier = Modifier.height(28.dp))
            // Subtitle
            Text(
            text = "A connection that moves you forward.",
            color = SubtitleGray,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
            )
        }
    }
}
@Preview(showBackground=true) @Composable private fun SplashScreenPreview(){
    JeelTheme{
        SplashScreen()
    }
}
