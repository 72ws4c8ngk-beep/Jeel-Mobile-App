
package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.state.JeelUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun ColumnScope.MentorEditProfileScreen(state: JeelUiState) {

    val draft = remember(state) {
        JeelUiState(
            state.role,
            state.name,
            state.email
        ).apply {
            applyProfile(
                state.profileValues(),
                state.name,
                state.email
            )
        }
    }

    with(draft) {
        PageHead(
            "EDIT PROFILE · MENTOR",
            "Your experience, their possibility",
            state::back
        )

        ProfileHero(
            name,
            professionalTitle,
            photo,
            "Editing your ${role.lowercase()} profile"
        )

        TextButton(onClick = { photoOptions = !photoOptions }) {
            Text("Change profile photo", color = Gold)
        }

        if (photoOptions) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    R.drawable.jeel_aisha,
                    R.drawable.jeel_omar,
                    R.drawable.jeel_leila
                ).forEach { image ->
                    Box(
                        Modifier.clickable {
                            photo = image
                            photoOptions = false
                        }
                    ) {
                        Avatar(image, 48)
                    }
                }
            }
        }

        Heading("Personal information", 18)

        EditBox("Age", age) {
            age = it
        }

        EditBox("Full name", name) {
            name = it
        }

        EditBox("Email", email) {
            email = it
        }

        EditBox("Location", location) {
            location = it
        }

        Heading("Professional background", 18)

        EditBox("Professional title", professionalTitle) {
            professionalTitle = it
        }

        Picker(
            "Industry",
            industry,
            listOf(
                "Fintech",
                "Technology",
                "Education",
                "Healthcare"
            )
        ) {
            industry = it
        }

        EditBox("Years of experience", years) {
            years = it.filter(Char::isDigit)
        }

        EditBox("Professional bio", bio, 5) {
            bio = it
        }

        TagEditor(
            "Expertise & skills",
            "Add skill",
            listOf(
                "Product strategy",
                "Leadership",
                "Roadmapping",
                "Stakeholder communication",
                "UX & design",
                "Data & analytics"
            ),
            expertise
        ) {
            expertise = it
        }

        TagEditor(
            "Mentorship topics",
            "Add topic",
            listOf(
                "Career roadmap",
                "CV review",
                "Leadership",
                "Interview preparation"
            ),
            topics
        ) {
            topics = it
        }

        Picker(
            "Session language",
            "English",
            listOf("English")
        ) {}

        Caption("Online mentorship · 45 min sessions")

        Info(
            "A profile that grows with you",
            "Keep your experience, expertise and mentorship topics up to date so mentees can find the right guidance."
        )

        Action(
            if (state.profileSaving) "Saving..." else "Save changes",
            enabled = !state.profileSaving
        ) {
            state.saveProfile(draft)
        }

        Action("Discard changes", secondary = true) {
            state.confirmation = "discard"
        }
    }
}
