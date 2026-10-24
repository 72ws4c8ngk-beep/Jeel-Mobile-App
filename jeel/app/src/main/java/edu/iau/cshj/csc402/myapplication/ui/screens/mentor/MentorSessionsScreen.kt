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
        when(sessionTab){
            "Upcoming"->{
                SessionCard("Yusuf Ali","Business graduate","Interview preparation",if(mentorCompleted)"Completed" else "Upcoming",R.drawable.jeel_yusuf,"Fri, 16 Oct 2026 · 11:00–11:45 AM"){
                    session("Yusuf Ali","Interview preparation")
                }
                SessionCard("Aisha Rahman","Final-year Computer Science student","Career roadmap","Upcoming",R.drawable.jeel_aisha,"Sun, 18 Oct 2026 · 10:00–10:45 AM"){
                    session("Aisha Rahman","Career roadmap")
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

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorSessionsScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="sessions"){}
}
