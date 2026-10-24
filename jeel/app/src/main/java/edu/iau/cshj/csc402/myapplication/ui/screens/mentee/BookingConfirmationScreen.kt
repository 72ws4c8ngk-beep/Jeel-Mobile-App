package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.BookingConfirmationScreen(state:JeelUiState) {
    with(state){
        PageHead("BOOKING CONFIRMATION","A step forward")
        Spacer(Modifier.height(16.dp))
        Box(Modifier.align(Alignment.CenterHorizontally).size(76.dp).clip(CircleShape).background(Color(0xFFE5F1ED)),contentAlignment=Alignment.Center){
            Text("✓",color=Green,fontSize=35.sp)
        }
        Heading("Your request is on its way.",25)
        BodyText("You've made time for your future. Your mentor will confirm your session soon.")
        Panel{
            Person(selectedMentor.name,selectedMentor.job,selectedMentor.image)
            Badge("Pending")
            SessionDetails(sessionDate,sessionTime,currentSession)
        }
        Caption("Preview only — no booking or email has been sent.")
        Action("View my sessions"){
            sessionTab="Upcoming"
            tab("sessions")
        }
        Action("Back to home",secondary=true){
            tab("home")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun BookingConfirmationScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="confirmation"){}
}
