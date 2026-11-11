package edu.iau.cshj.csc402.myapplication.ui.screens.shared
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

import edu.iau.cshj.csc402.myapplication.R
@Composable
internal fun ColumnScope.SessionDetailsScreen(state: JeelUiState) {
    with(state) {
        val preview=LocalUiScene.current.state
        if (useRoomSessions && selectedSessionId != null) {
            val row = selectedSavedSession
            PageHead("SESSIONS", row?.person ?: "Session unavailable", ::back)
            if (row == null) return
            Panel {
                Badge(row.entity.status)
                SessionDetails(row.entity.sessionDate, row.entity.sessionTime, row.topic)
                if (row.note.isNotBlank()) BodyText(row.note)
            }
            return
        }
        val status=when {
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
            Action("Mark as completed",enabled=status=="Upcoming") { }
        } else {
            Info("Come with one question","Think about the career decision you'd most like clarity on. Bring a notebook and your curiosity.")
        }
        when(status) {
            "Upcoming" -> {
                Action("Reschedule",secondary=true) { rescheduling=true; go("book") }
                DangerButton("Cancel session") { confirmation="cancel" }
            }
            "Completed" -> if(!isMentor) Action("Review session") { go("review") }
            "Pending" -> Info("Waiting for mentor approval","You will be notified when the mentor responds.")
            "Cancelled" -> Info("Session cancelled","This appointment is no longer in your upcoming sessions.")
        }
    }
}
