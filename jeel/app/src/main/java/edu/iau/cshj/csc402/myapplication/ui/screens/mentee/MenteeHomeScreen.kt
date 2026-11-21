package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.JeelRepository
import edu.iau.cshj.csc402.myapplication.data.toDisplayMentor
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeHomeScreen(
    state: JeelUiState,
    repository: JeelRepository,
    menteeId: Int?
) {
    val goals = if (menteeId != null) {
        val observedGoals by remember(repository, menteeId) {
            repository.observeGoals(menteeId)
        }.collectAsState(initial = null)
        observedGoals
    } else {
        null
    }
    val totalTasks = goals.orEmpty().sumOf { it.actions.size }
    val completedTasks = goals.orEmpty().sumOf { goal -> goal.actions.count { it.isCompleted } }
    val progress = if (totalTasks == 0) 0f else completedTasks.toFloat() / totalTasks

    val sessionDateFormat = remember {
        SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("Asia/Riyadh")
            isLenient = false
        }
    }
    val now = System.currentTimeMillis()
    val upcomingSession = state.menteeSessions.asSequence()
        .filter { it.menteeId == menteeId && it.status == "Accepted" }
        .mapNotNull { booking ->
            val dateTime = "${booking.sessionDate.trim()} ${booking.sessionTime.trim()}"
            val position = ParsePosition(0)
            val startsAt = sessionDateFormat.parse(dateTime, position)?.time
            if (startsAt != null && position.index == dateTime.length && startsAt >= now) {
                booking to startsAt
            } else {
                null
            }
        }
        .minWithOrNull(compareBy({ it.second }, { it.first.sessionId }))
        ?.first
    with(state){
        PageHead("JEEL · MENTEE","Your next chapter",trailing={
            TextButton(onClick={
                go("notifications")
            }){
                JeelOutlineIcon("notifications")
            }
        })
        Row(verticalAlignment=Alignment.CenterVertically){
            Column(Modifier.weight(1f)){
                Heading("Good morning, ${name.substringBefore(' ')}",23)
                Caption("Small steps. Meaningful progress.")
            }
            Avatar(photo,44)
        }
        Panel(Navy){
            Text("BUILT ON REAL EXPERIENCE",fontSize=10.sp,color=Color.White.copy(alpha=.7f))
            Text("You don't have to figure it all out alone.",fontFamily=JeelSerif,color=Color.White,fontSize=25.sp,lineHeight=30.sp)
            Text("Meet mentors who understand your ambition.",color=Color.White.copy(alpha=.7f),fontSize=12.sp)
        }
        Panel{
            Heading("Find your next mentor",18)
            BodyText("Explore professionals whose experience fits your interests and goals.")
            Action("Explore mentors") { tab("explore") }
        }
        Section("Recommended for you","See all"){
            tab("explore")
        }
        val featuredMentor = mentorProfiles.firstOrNull()
        if (featuredMentor == null) {
            BodyText("No mentors available yet.")
        } else {
            MentorCard(featuredMentor.toDisplayMentor()) {
                openRoomMentor(featuredMentor.mentorId)
            }
        }
        Heading("Explore by expertise",18)
        Chips(listOf("Product & tech","Business","Design","Data & analytics"),""){
            filterArea=when(it){
                "Product & tech"->"Product"
                "Business"->"Leadership"
                else->it
            }
            tab("explore")
        }
        Section("Your upcoming session","View all"){
            tab("sessions")
        }
        if (upcomingSession != null) {
            val mentor = mentorProfiles.firstOrNull { it.mentorId == upcomingSession.mentorId }
            SessionCard(
                mentor?.fullName ?: "Mentor",
                mentor?.specialization ?: "Mentorship",
                "Mentorship session",
                "Upcoming",
                R.drawable.jeel_omar,
                "${upcomingSession.sessionDate} · ${upcomingSession.sessionTime}"
            ) { openSession(upcomingSession) }
        } else {
            Panel {
                Heading("No upcoming sessions", 16)
                BodyText("Your next accepted session will appear here.")
                TextButton(onClick = { tab("explore") }) { Text("Explore mentors") }
            }
        }

        Section("Your progress", "View progress") { tab("progress") }
        if (goals == null) {
            BodyText(if (menteeId == null) "Progress is unavailable." else "Loading progress…")
        } else {
            Box(Modifier.fillMaxWidth().clickable(onClickLabel = "Open My Progress") {
                tab("progress")
            }) {
                ProgressCard(
                    "Overall task progress",
                    progress,
                    if (totalTasks == 0) "Add a goal and a task to get started."
                    else "$completedTasks of $totalTasks tasks complete"
                )
            }
        }
        mentorProfiles.getOrNull(1)?.let { mentor ->
            Heading("More mentors to explore", 18)
            MentorCard(mentor.toDisplayMentor()) {
                openRoomMentor(mentor.mentorId)
            }
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun MenteeHomeScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="home"){}
}
