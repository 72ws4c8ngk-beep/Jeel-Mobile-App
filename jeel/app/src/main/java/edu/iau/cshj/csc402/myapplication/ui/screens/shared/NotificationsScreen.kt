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

@Composable
internal fun ColumnScope.NotificationsScreen(state: JeelUiState) {
    with(state) {
        val preview=LocalUiScene.current.state
        val read=allRead || preview=="Read"
        PageHead("JEEL · "+role.uppercase(),"Notifications",::back)
        BodyText("For "+name.removePrefix("Dr. ").substringBefore(' ')+" · "+role)
        Caption(if(read) "All caught up · No unread notifications" else "2 unread · Updates for your mentorships")
        TextButton(onClick={ allRead=true }) { Text("Mark all as read",color=Gold) }
        val messages=listOf(
            "Session reminder" to "Your 45 min Zoom session is coming up: Interview preparation.",
            "Booking confirmed" to "Career roadmap is confirmed. Your Zoom link will appear in Session Details.",
            "Request accepted" to if(isMentor) "You accepted Aisha's mentorship request." else "Your mentor accepted your request.",
            "Request rejected" to if(isMentor) "The mentee has been notified of your decision." else "Your request was rejected. Explore other mentors for your next conversation."
        )
        messages.forEachIndexed { index,(title,body) ->
            Panel {
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) {
                    BodyText(title)
                    Badge(if(!read&&index<2) "Unread" else "Read")
                }
                BodyText(body)
                Caption("Sun, 18 Oct 2026 · 10:00–10:45 AM · UTC+3")
                TextButton(onClick={ go(if(isMentor&&index>=2) "request" else "session") }) { Text(if(isMentor&&index>=2) "View request" else "View session",color=Navy) }
            }
        }
    }
}
