package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import edu.iau.cshj.csc402.myapplication.state.JeelUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*

@Composable
internal fun ColumnScope.NotificationsScreen(state: JeelUiState) {
    with(state) {
        PageHead("JEEL · " + role.uppercase(), "Notifications", ::back)
        val items = notificationSessions
        val unread = items.count { notificationKey(it) !in readNotificationKeys }
        Caption("$unread unread · Updates for your mentorships")
        if (items.isEmpty()) Info("No notifications yet", "Booking updates will appear here.")
        if (unread > 0) TextButton(onClick = { markNotificationsRead(items.map { notificationKey(it) }) }) { Text("Mark all as read") }
        items.forEach { booking ->
            val key = notificationKey(booking)
            val title = when {
                booking.rating != null -> "Session feedback received"
                booking.status == "Pending" -> if (isMentor) "New booking request" else "Booking request sent"
                booking.status == "Accepted" -> "Booking accepted"
                booking.status == "Rejected" -> "Booking rejected"
                booking.status == "Completed" -> "Session completed"
                booking.status == "Cancelled" -> "Session cancelled"
                else -> "Session update"
            }
            Panel {
                Heading(title, 18)
                Badge(if(key in readNotificationKeys) "Read" else "Unread")
                val person = if(isMentor) menteeNames[booking.menteeId] ?: "Mentee"
                    else mentorProfiles.firstOrNull { it.mentorId == booking.mentorId }?.fullName ?: "Mentor"
                BodyText(person)
                Caption("${booking.sessionDate} · ${booking.sessionTime}")
                if (booking.rating != null) BodyText("${booking.rating} out of 5 stars")
                TextButton(onClick = {
                    markNotificationsRead(listOf(key))
                    if (isMentor && booking.status == "Pending") {
                        selectedRequestId = booking.sessionId
                        requestPerson = person
                        go("request")
                    } else openSession(booking)
                }) { Text(if(isMentor && booking.status == "Pending") "View request" else "View session") }
            }
        }
    }
}
