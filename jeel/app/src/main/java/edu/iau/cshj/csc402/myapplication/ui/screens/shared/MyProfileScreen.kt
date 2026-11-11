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
internal fun ColumnScope.MyProfileScreen(state: JeelUiState) {
    with(state) {
        PageHead("JEEL · "+role.uppercase(), "Your profile")
        ProfileHero(name, if(isMentor) professionalTitle+" · "+industry else studyStage+" · "+study, photo, if(isMentor) "Verified mentor" else "Mentee")
        Action("Edit Profile", secondary=true) { go("editProfile") }
        Action("Notifications", secondary=true) { go("notifications") }
        if(isMentor) {
            val reviews = mentorSessions.filter { it.rating != null }
            val average = if(reviews.isEmpty()) "—" else "%.1f".format(reviews.mapNotNull { it.rating }.average())
            Stats(listOf(years to "Years of experience", average to "Average rating", mentorSessions.count { it.status == "Completed" }.toString() to "Sessions guided"))
            Heading("Professional bio",18)
            BodyText(bio)
            Heading("Expertise & skills",18)
            Labels(expertise)
            Heading("Mentorship topics",18)
            Labels(topics)
            Heading("Latest review",18)
            val latest = mentorSessions.lastOrNull { it.rating != null }
            if (latest == null) Caption("No reviews yet") else Panel {
                BodyText("${latest.rating} out of 5 stars")
                BodyText(latest.feedback)
            }
        }
        Heading("Personal information",18)
        Panel {
            Detail("✉", "Email", email)
            Detail("◷", "Age", age+" years")
            Detail("◎", "Location", location)
            if(!isMentor) {
                Detail("◇", "Field of study", study)
                Detail("◇", "Study stage", studyStage)
                Detail("◇", "University", university)
            }
        }
        if(!isMentor) {
            Heading("Your interests",18)
            Labels(interests)
            Heading("Your career goal",18)
            Info(careerGoal,"Focus skills: "+skills.joinToString())
            Heading("Preferred topics",18)
            Labels(topics)
        } else {
            Action("Manage availability",secondary=true) { go("availability") }
        }
        Action("Settings",secondary=true) { go("settings") }
        Caption("Jeel 1.0 · Guidance for every generation")
    }
}
