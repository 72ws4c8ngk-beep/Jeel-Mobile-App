package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeSessionDetailScreen(state:JeelUiState) {
    with(state){
        PageHead("${"MY SESSIONS"} · ${if(cancelled)"CANCELLED" else "UPCOMING"}","Your session",::back)
        Panel{
            Person(sessionPerson.ifBlank{
                selectedMentor.name
            },selectedMentor.job,selectedMentor.image)
            Badge(if(cancelled)"Cancelled" else if(bookingPending)"Pending" else "Upcoming")
            SessionDetails(sessionDate,sessionTime,currentSession)
            Detail("▣","Location","Online · Join details appear before the session")
        }
        Heading("Your note to ${selectedMentor.name.removePrefix("Dr. ").substringBefore(' ')}",18)
        Panel{
            BodyText(bookingNote)
        }
        Info("Come with one question","Think about the career decision you'd most like clarity on. Bring a notebook and your curiosity.")
        if(!cancelled){
            Action("Reschedule",secondary=true){
                rescheduling=true
                go("reschedule")
            }
            DangerButton("Cancel session"){
                confirmation="cancel"
            }
        }
        Caption("Please make changes at least 24 hours before your session.")
        Caption("You can leave a review once the session is completed.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeSessionDetailScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="session"){}
}
