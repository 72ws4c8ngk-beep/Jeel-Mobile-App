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
        val selectedSession = mentorSessions.firstOrNull {
            it.sessionId == selectedRequestId
        }
        if (selectedSession == null) {
            PageHead(
                "REQUEST DETAILS",
                "Request not found",
                ::back
            )

            Info(
                "No request selected",
                "Please return to Mentorship Requests and select a booking."
            )

            return@with
        }
        PageHead(
            "REQUESTS · ${selectedSession?.status?.uppercase() ?: "NOT FOUND"}",
            "Meet ${requestPerson.substringBefore(' ')}",
            ::back
        )
        Panel {
            Person(
                requestPerson,
                "Mentee",
                R.drawable.jeel_aisha
            )

            Badge(selectedSession?.status ?: "Unknown")

            Detail(
                "◇",
                "Request ID",
                selectedSession?.sessionId?.toString() ?: "Unavailable"
            )

            Detail(
                "◎",
                "Session status",
                selectedSession?.status ?: "Unavailable"
            )
        }

        Panel(Mist){
            SessionDetails(
                selectedSession?.sessionDate ?: "Date unavailable",
                selectedSession?.sessionTime ?: "Time unavailable",
                "Mentorship session"
            )
            Detail("▣","Format","Zoom session · English")
        }
        Heading("Booking notes", 18)
        Panel {
            BodyText(
                selectedSession?.notes?.takeIf { it.isNotBlank() }
                    ?: "No notes provided."
            )
        }
        if (selectedSession?.status == "Pending") {
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
