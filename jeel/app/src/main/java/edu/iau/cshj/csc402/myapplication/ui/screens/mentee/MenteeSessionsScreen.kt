package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.state.JeelUiState
import edu.iau.cshj.csc402.myapplication.ui.components.Heading
import edu.iau.cshj.csc402.myapplication.ui.components.Info
import edu.iau.cshj.csc402.myapplication.ui.components.PageHead
import edu.iau.cshj.csc402.myapplication.ui.components.Segments
import edu.iau.cshj.csc402.myapplication.ui.components.SessionCard

@Composable
internal fun ColumnScope.MenteeSessionsScreen(state: JeelUiState) {
    with(state) {
        PageHead("MY SESSIONS", "Your conversations")
        Segments(listOf("Upcoming", "Completed", "Cancelled"), sessionTab) { sessionTab = it }
        Heading(sessionTab, 18)
        val sessions = menteeSessions.filter {
            when(sessionTab) {
                "Completed" -> it.status == "Completed"
                "Cancelled" -> it.status == "Cancelled"
                else -> it.status in listOf("Pending", "Accepted")
            }
        }
        if (sessions.isEmpty()) Info("No sessions", "Your sessions will appear here.")
        sessions.forEach { booking ->
            val mentor = mentorProfiles.firstOrNull { it.mentorId == booking.mentorId }
            SessionCard(
                mentor?.fullName ?: "Mentor",
                mentor?.specialization ?: "Mentorship",
                "Mentorship session",
                if (booking.status == "Accepted") "Upcoming" else booking.status,
                R.drawable.jeel_omar,
                "${booking.sessionDate} · ${booking.sessionTime}"
            ) { openSession(booking) }
        }
    }
}
