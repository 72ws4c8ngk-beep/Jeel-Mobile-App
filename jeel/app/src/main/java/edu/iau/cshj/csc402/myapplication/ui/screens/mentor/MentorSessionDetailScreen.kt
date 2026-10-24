package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MentorSessionDetailScreen(state:JeelUiState) {
    with(state){
        PageHead("${"SESSIONS"} · ${if(mentorCompleted)"COMPLETED" else if(cancelled)"CANCELLED" else "UPCOMING"}","Guide the next step",::back)
        Panel{
            Person(sessionPerson.ifBlank{
                "Aisha Rahman"
            },"Final-year Computer Science student",
                if(sessionPerson=="Yusuf Ali")R.drawable.jeel_yusuf else R.drawable.jeel_aisha
            )
            Badge(if(cancelled)"Cancelled" else if(mentorCompleted)"Completed" else "Upcoming")
            SessionDetails(sessionDate,sessionTime,currentSession)
            Detail("▣","Location","Online · Join details appear before the session")
        }
        Heading("$sessionPerson's note",18)
        Panel{
            BodyText(bookingNote)
        }
        Heading("Add session notes",18)
        EditBox("Private preparation notes",sessionNotes,3){
            sessionNotes=it
        }
        Caption("Only you can see these notes.")
        Action("Save session notes",secondary=true){
        }
        Heading("Action items for ${sessionPerson.substringBefore(' ')}",18)
        actionTitles.forEachIndexed{
            i,t->Panel{
                EditBox("Action title",t){
                }
                EditBox("Due date",actionDueDates[i]){
                }
                ToggleRow("Share with mentee",actionShared[i]){
                }
            }
        }
        Action("Add action item · ${actionTitles.size}/3",secondary=true,enabled=actionTitles.size<3){
        }
        Info("Help turn advice into action","After the session, share a clear next step and mark the conversation completed.")
        Action(if(mentorCompleted)"Session completed" else "Mark as completed",enabled=!mentorCompleted){
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorSessionDetailScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="session"){}
}
