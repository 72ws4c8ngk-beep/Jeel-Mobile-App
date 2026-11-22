
package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace
import edu.iau.cshj.csc402.myapplication.state.JeelUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun parseMentorSessionDateTime(
    date: String,
    time: String
): Date? {
    return runCatching {
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd h:mm a",
            Locale.US
        ).apply {
            isLenient = false
        }

        formatter.parse(
            "${date.trim()} ${time.trim().uppercase(Locale.US)}"
        )
    }.getOrNull()
}

@Composable
internal fun ColumnScope.MentorHomeScreen(state: JeelUiState) {
    with(state) {

        val completedSessions = mentorSessions.filter {
            it.status == "Completed"
        }

        val ratings = completedSessions.mapNotNull {
            it.rating
        }

        val averageRating = if (ratings.isNotEmpty()) {
            String.format(Locale.US, "%.1f", ratings.average())
        } else {
            "—"
        }

        val pendingRequests = mentorSessions.filter {
            it.status == "Pending"
        }

        val now = Date()

        // Nearest future accepted session
        val nextSession = mentorSessions
            .asSequence()
            .filter { it.status == "Accepted" }
            .mapNotNull { booking ->
                parseMentorSessionDateTime(
                    booking.sessionDate,
                    booking.sessionTime
                )?.let { dateTime ->
                    booking to dateTime
                }
            }
            .filter { (_, dateTime) ->
                !dateTime.before(now)
            }
            .minByOrNull { (_, dateTime) -> dateTime }
            ?.first

        // Oldest pending request with a valid date
        val nextRequest = pendingRequests
            .mapNotNull { booking ->
                parseMentorSessionDateTime(
                    booking.sessionDate,
                    booking.sessionTime
                )?.let { dateTime ->
                    booking to dateTime
                }
            }
            .minByOrNull { (_, dateTime) -> dateTime }
            ?.first

        PageHead(
            "JEEL · MENTOR",
            "Experience that matters",
            trailing = {
                TextButton(onClick = { go("notifications") }) {
                    JeelOutlineIcon("notifications")
                }
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Heading("Welcome back, $name", 23)
                Caption("Your guidance is making a difference.")
            }
            Avatar(photo, 44)
        }

        Panel(Navy) {
            Text(
                "YOUR EXPERIENCE. THEIR POSSIBILITY.",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.7f)
            )

            Text(
                "A lifetime of learning.\nA new generation of growth.",
                fontFamily = JeelSerif,
                color = Color.White,
                fontSize = 25.sp,
                lineHeight = 30.sp
            )
        }

        Stats(
            listOf(
                completedSessions.size.toString() to "Sessions guided",
                averageRating to "Avg. rating",
                pendingRequests.size.toString() to "Pending"
            )
        )

        Section("Upcoming session", "View all") {
            sessionTab = "Upcoming"
            tab("sessions")
        }

        if (nextSession != null) {
            SessionCard(
                menteeNames[nextSession.menteeId] ?: "Mentee",
                "Mentee",
                "Mentorship session",
                "Upcoming",
                R.drawable.jeel_aisha,
                "${nextSession.sessionDate} · ${nextSession.sessionTime}"
            ) {
                openSession(nextSession)
            }
        } else {
            Info(
                "No upcoming sessions",
                "Your accepted upcoming sessions will appear here."
            )
        }

        Section(
            "Pending requests · ${pendingRequests.size}",
            "Review all"
        ) {
            requestTab = "Pending"
            tab("requests")
        }

        if (nextRequest != null) {
            Panel {
                Person(
                    menteeNames[nextRequest.menteeId] ?: "Mentee",
                    "Mentee",
                    R.drawable.jeel_aisha
                )

                Badge("Pending")

                BodyText(
                    "${nextRequest.sessionDate} · ${nextRequest.sessionTime}"
                )

                if (nextRequest.notes.isNotBlank()) {
                    Caption(nextRequest.notes)
                }

                Action("Review request", secondary = true) {
                    selectedRequestId = nextRequest.sessionId
                    requestPerson =
                        menteeNames[nextRequest.menteeId] ?: "Mentee"
                    go("request")
                }
            }
        } else {
            Info(
                "You're all caught up",
                "You have no pending requests."
            )
        }

        Heading("Quick actions", 18)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.weight(1f)) {
                Action("Availability", secondary = true) {
                    go("availability")
                }
            }

            Box(Modifier.weight(1f)) {
                Action("Edit profile", secondary = true) {
                    go("editProfile")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun MentorHomeScreenPreview() {
    JeelWorkspace(
        "Mentor",
        "",
        "",
        initialRoute = "home"
    ) {}
}
