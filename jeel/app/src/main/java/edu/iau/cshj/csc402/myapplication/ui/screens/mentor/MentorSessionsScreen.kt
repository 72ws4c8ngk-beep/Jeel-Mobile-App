package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

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
internal fun ColumnScope.MentorSessionsScreen(state:JeelUiState) {
    with(state){
        PageHead("YOUR SESSIONS","Conversations that count")
        BodyText("A little of your time. A lasting impact.")
        Segments(listOf("Upcoming","Completed","Cancelled"),sessionTab){
            sessionTab=it
        }
        Heading(sessionTab,18)
        val acceptedSessions = mentorSessions.filter {
            it.status == "Accepted"
        }
        when(sessionTab){
            "Upcoming" -> {
                if (acceptedSessions.isEmpty()) {
                    Info(
                        "No upcoming sessions",
                        "Accepted mentorship requests will appear here."
                    )
                } else {
                    acceptedSessions.forEach { booking ->
                        val menteeName =
                            menteeNames[booking.menteeId] ?: "Unknown Mentee"

                        SessionCard(
                            menteeName,
                            "Mentee",
                            "Mentorship session",
                            "Upcoming",
                            R.drawable.jeel_aisha,
                            "${booking.sessionDate} · ${booking.sessionTime}"
                        ) {
                            selectedRequestId = booking.sessionId
                            session(menteeName, "Mentorship session")
                        }
                    }
                }
            }
            "Completed"->{
                SessionCard("Aisha Rahman","Computer Science student","Career roadmap","Completed",R.drawable.jeel_aisha,"4 Oct 2026 · 10:00–10:45 AM"){
                    currentSession="Career roadmap"
                    session("Aisha Rahman","Career roadmap")
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

    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun MentorSessionsScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="sessions"){}
}
