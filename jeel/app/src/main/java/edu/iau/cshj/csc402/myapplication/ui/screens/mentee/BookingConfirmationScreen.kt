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
        Heading(if(rescheduling) "Your session has been rescheduled." else if(bookingPending) "Waiting for mentor approval" else "Your session is booked.",25)
        BodyText(if(bookingPending) "Your request was sent. You will be notified when the mentor responds." else "Your mentorship session is confirmed. We look forward to seeing you on Zoom.")
        Panel{
            Person(selectedMentor.name,selectedMentor.job,selectedMentor.image)
            Badge(if(bookingPending) "Pending" else if(rescheduling) "Rescheduled" else "Confirmed")
            SessionDetails(sessionDate,sessionTime,currentSession)
        }
        Caption("Your Zoom link will be available in Session Details before the session.")
        Action("View my sessions"){
            sessionTab="Upcoming"
            tab("sessions")
        }
        Action("Back to home",secondary=true){
            tab("home")
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun BookingConfirmationScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="confirmation"){}
}
