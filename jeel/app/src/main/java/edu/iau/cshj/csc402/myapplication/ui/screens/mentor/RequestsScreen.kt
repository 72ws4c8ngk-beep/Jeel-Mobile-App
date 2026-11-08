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
internal fun ColumnScope.RequestsScreen(state:JeelUiState) {
    with(state){
        PageHead("MENTORSHIP REQUESTS","Someone's next step")
        BodyText("Take a moment to see where your experience can help.")
        Segments(listOf("Pending","Accepted","Rejected"),requestTab){
            requestTab=it
        }
        Heading(if(requestTab=="Pending")"Awaiting your response" else "$requestTab requests",18)
        val filteredRequests = mentorSessions.filter { it.status == requestTab }
        val visible = filteredRequests.size
        filteredRequests.forEach { session ->
            val menteeName = menteeNames[session.menteeId] ?: "Unknown Mentee"

            Panel {
                Section("Session Request", "• ${session.status}") {}

                Person(
                    menteeName,
                    "Mentee",
                    R.drawable.jeel_aisha
                )

                BodyText("Mentorship session")

                Caption("${session.sessionDate} · ${session.sessionTime}")

                Action("View request ↗️", secondary = true) {
                    selectedRequestId = session.sessionId
                    requestPerson = menteeName
                    go("request")
                }
            }
        }
        if(visible==0)Info("No ${requestTab.lowercase()} requests","Requests with this status will appear here.")
        Info("A thoughtful reply matters","Please respond within 48 hours. Accepted requests appear in your upcoming sessions.")
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun RequestsScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="requests"){}
}
