package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeSessionsScreen(state:JeelUiState) {
    with(state){
        PageHead("MY SESSIONS","Your conversations")
        BodyText("Every conversation brings you closer.")
        Segments(listOf("Upcoming","Completed","Cancelled"),sessionTab){
            sessionTab=it
        }
        Heading(sessionTab,18)
        when(sessionTab){
            "Upcoming"->{
                if(!cancelled)SessionCard(if(bookingPending)sessionPerson else mentors[0].name,mentors[mentorIndex].job,if(bookingPending)currentSession else "Career roadmap",if(bookingPending)"Pending" else "Upcoming",mentors[mentorIndex].image,"$sessionDate · $sessionTime · 45 min"){
                    session(if(bookingPending)sessionPerson else mentors[0].name,if(bookingPending)currentSession else "Career roadmap")
                }
                SessionCard(mentors[1].name,mentors[1].job,"Portfolio review","Upcoming",mentors[1].image,"Sun, 25 Oct 2026 · 11:00–11:45 AM"){
                    mentorIndex=1
                    session(mentors[1].name,"Portfolio review")
                }
            }
            "Completed"->{
                SessionCard(mentors[0].name,mentors[0].job,"Career roadmap","Completed",mentors[0].image,"4 Oct 2026 · 10:00–10:45 AM"){
                    currentSession="Career roadmap"
                    go("review")
                }
                if(reviewSent)Info("Thank you for reflecting","Your review is saved in this preview.")
            }
            "Cancelled"->{
                if(cancelled)SessionCard(sessionPerson.ifBlank{
                    mentors[0].name
                },"Mentorship","Career roadmap","Cancelled",mentors[0].image,sessionDate){
                } else Info("No cancelled sessions","Your cancelled conversations will appear here.")
            }
        }
        Caption("Join details appear in your session 15 minutes before the start.")
        Action("Find another mentor",secondary=true){
            tab("explore")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeSessionsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="sessions"){}
}
