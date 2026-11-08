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
        val upcomingSessions = menteeSessions.filter {
            it.status == "Pending" || it.status == "Accepted"
        }
        when(sessionTab){
            "Upcoming" -> {
                if (upcomingSessions.isEmpty()) {
                    Info(
                        "No upcoming sessions",
                        "Your booked sessions will appear here."
                    )
                } else {
                    upcomingSessions.forEach { bookedSession ->
                        val bookedMentor = mentorProfiles.firstOrNull {
                            it.mentorId == bookedSession.mentorId
                        }

                        SessionCard(
                            bookedMentor?.fullName ?: "Mentor",
                            bookedMentor?.specialization ?: "Mentorship",
                            "Mentoring session",
                            bookedSession.status,
                            mentors[mentorIndex].image,
                            "${bookedSession.sessionDate} · ${bookedSession.sessionTime} · 45 min"
                        ) {
                            session(
                                bookedMentor?.fullName ?: "Mentor",
                                "Mentoring session"
                            )
                        }
                    }
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

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun MenteeSessionsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="sessions"){}
}
