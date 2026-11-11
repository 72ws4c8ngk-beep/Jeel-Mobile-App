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
internal fun ColumnScope.RequestDetailScreen(state:JeelUiState) {
    with(state){
        if (useRoomSessions) {
            val row = selectedSavedSession
            PageHead("MENTORSHIP REQUEST", row?.person ?: "Request unavailable", ::back)
            if (row == null) return
            Panel {
                Badge(row.entity.status)
                SessionDetails(row.entity.sessionDate, row.entity.sessionTime, row.topic)
                if (row.note.isNotBlank()) BodyText(row.note)
            }
            if (notice.isNotBlank()) Info("Request update", notice)
            if (row.entity.status == "Pending" && !savingSession) {
                Action("Accept request") { confirmation = "accept" }
                DangerButton("Reject request") { confirmation = "reject" }
            }
            return
        }
        PageHead("REQUESTS · ${requestStates[requesterIndex].uppercase()}","Meet ${requestPerson.substringBefore(' ')}",::back)
        Panel{
            Person(requestPerson,if(requesterIndex==0)"Final-year Computer Science student" else "Business graduate",if(requesterIndex==0)R.drawable.jeel_aisha else R.drawable.jeel_yusuf)
            Badge(requestStates[requesterIndex])
            Detail("◇","Field of study",if(requesterIndex==0)"Computer Science · King Saud University" else "Business administration")
            Detail("◎","Career goal",if(requesterIndex==0)Goal else "Build confidence for my first consulting role")
        }
        Heading("Where they'd like guidance",18)
        Chips(listOf("Product management","Technology","Product thinking"),""){
        }
        Panel(Mist){
            SessionDetails("Sunday, 18 October 2026","10:00 AM",if(requesterIndex==0)"Career roadmap" else "Leadership foundations")
            Detail("▣","Format","Zoom session · English")
        }
        Heading("A message from ${requestPerson.substringBefore(' ')}",18)
        Panel{
            BodyText(Note)
        }
        Info("Suggested match based on their goal and your expertise.","Their goals align with your career direction and product strategy topics.")
        if(requestStates[requesterIndex]=="Pending"){
            Action("✓ Accept request"){
                confirmation="accept"
            }
            DangerButton("Reject request"){
                confirmation="reject"
            }
        } else Action("View sessions",secondary=true){
            tab("sessions")
        }
        Caption("Meet online with Zoom · 45 min mentorship")
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun RequestDetailScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="request"){}
}
