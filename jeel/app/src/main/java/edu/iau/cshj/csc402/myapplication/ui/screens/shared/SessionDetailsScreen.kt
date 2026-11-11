package edu.iau.cshj.csc402.myapplication.ui.screens.shared
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.state.JeelUiState
import edu.iau.cshj.csc402.myapplication.ui.components.Action
import edu.iau.cshj.csc402.myapplication.ui.components.Badge
import edu.iau.cshj.csc402.myapplication.ui.components.BodyText
import edu.iau.cshj.csc402.myapplication.ui.components.Caption
import edu.iau.cshj.csc402.myapplication.ui.components.DangerButton
import edu.iau.cshj.csc402.myapplication.ui.components.Detail
import edu.iau.cshj.csc402.myapplication.ui.components.EditBox
import edu.iau.cshj.csc402.myapplication.ui.components.Heading
import edu.iau.cshj.csc402.myapplication.ui.components.Info
import edu.iau.cshj.csc402.myapplication.ui.components.LocalUiScene
import edu.iau.cshj.csc402.myapplication.ui.components.PageHead
import edu.iau.cshj.csc402.myapplication.ui.components.Panel
import edu.iau.cshj.csc402.myapplication.ui.components.Person
import edu.iau.cshj.csc402.myapplication.ui.components.SessionDetails
import edu.iau.cshj.csc402.myapplication.ui.components.ToggleRow


import edu.iau.cshj.csc402.myapplication.R
@Composable
internal fun ColumnScope.SessionDetailsScreen(state: JeelUiState) {
    with(state) {
        val preview=LocalUiScene.current.state
        val booking = selectedSession
        val status=booking?.status?.let { if(it == "Accepted") "Upcoming" else it } ?: when {
            preview=="Completed"||mentorCompleted -> "Completed"
            preview=="Cancelled"||cancelled -> "Cancelled"
            preview=="Pending approval"||bookingPending -> "Pending"
            else -> "Upcoming"
        }
        val person=if(isMentor) sessionPerson.ifBlank { "Aisha Rahman" } else sessionPerson.ifBlank { selectedMentor.name }
        val zoomReady=preview=="Zoom link available"
        PageHead("SESSIONS · "+status.uppercase(),if(isMentor) "Guide the next step" else "Your session",::back)
        Panel {
            Person(person,if(isMentor) "Final-year Computer Science student" else selectedMentor.job,if(isMentor) R.drawable.jeel_aisha else selectedMentor.image)
            Badge(status)
            SessionDetails(sessionDate,sessionTime,currentSession)
            Detail("▣","Meeting platform","Zoom")
            if(zoomReady) {
                Detail("↗","Zoom link","zoom.us/j/••••••••••")
                Action("Join Zoom meeting") { }
            } else {
                Caption("The Zoom link will be available before your session.")
                Action("Zoom link not available yet",enabled=false) { }
            }
        }
        Heading(if(isMentor) person.substringBefore(' ')+"'s note" else "Your note to "+selectedMentor.name.removePrefix("Dr. ").substringBefore(' '),18)
        Panel { BodyText(bookingNote) }
        if(isMentor) {
            Heading("Add session notes",18)
            EditBox("Private preparation notes",sessionNotes,3) { sessionNotes=it }
            Caption("Only you can see these notes.")
            Action("Save session notes",secondary=true) { }
            Heading("Action items",18)
            actionTitles.forEachIndexed { i,title ->
                Panel {
                    EditBox("Action title",title) { }
                    EditBox("Due date",actionDueDates[i]) { }
                    ToggleRow("Share with mentee",actionShared[i]) { }
                }
            }
            Action("Mark as completed",enabled=booking != null && status=="Upcoming" && !sessionSaving) { completeSelectedSession() }
        } else {
            Info("Come with one question","Think about the career decision you'd most like clarity on. Bring a notebook and your curiosity.")
        }
        when(status) {
            "Upcoming", "Pending" -> {
                if (status == "Pending") Info("Waiting for mentor approval", "Your request is pending.")
                if (booking != null && !sessionSaving) DangerButton("Cancel session") { confirmation="cancel" }
            }
            "Completed" -> {
                if (booking?.rating != null) {
                    Heading("Session feedback", 18)
                    Panel {
                        BodyText("${booking.rating} out of 5 stars")
                        if (booking.feedback.isNotBlank()) BodyText(booking.feedback)
                    }
                } else if(!isMentor && booking != null) Action("Review session") { go("review") }
            }

            "Cancelled" -> Info("Session cancelled","This appointment is no longer in your upcoming sessions.")
        }
    }
}
