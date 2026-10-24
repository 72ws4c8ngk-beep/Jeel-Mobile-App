package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeNotificationsScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL · ${role.uppercase()}","Notifications",::back)
        BodyText("For ${name.substringBefore(' ')} · $role")
        Caption("${if(allRead)0 else 2} unread · Updates for your mentorships\nArabia Standard Time (UTC+3)")
        TextButton(onClick={
        }){
            Text("Mark all as read",color=Gold)
        }
        listOf("Session reminder" to "Your 45 min session is coming up: Interview preparation.","Booking confirmed" to "Career roadmap is confirmed. You'll meet online for 45 min.","Request update" to "Your Career roadmap request has been updated. The appointment is in your Sessions.","Session update" to "Join details appear in Session details before each appointment.").forEachIndexed{
            i,(t,b)->Panel{
                Section(t,if(!allRead&&i<2)"• Unread" else "Read"){
                }
                BodyText(b)
                Caption("Sun, 18 Oct 2026 · 10:00–10:45 AM")
                TextButton(onClick={
                    tab("sessions")
                }){
                    Text("View session",color=Navy)
                }
            }
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeNotificationsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="notifications"){}
}
