package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.FilterScreen(state: JeelUiState) {
    with(state) {

        var filterStatus by remember { mutableStateOf("") }

        val matchCount = filteredRoomMentors().size

        PageHead("EXPLORE · FILTERS", "Refine your search", ::back)
        BodyText("Find the kind of experience you need.")

        if (!hasActiveMentorFilters) {
            Info(
                if (filterStatus == "reset")
                    "Filters reset"
                else
                    "No filters selected",
                "All specialties, skills and available times are included."
            )
        }

        Heading("Expertise", 18)

        Chips(
            listOf(
                "All expertise",
                "Product strategy",
                "UX & design",
                "Data & analytics",
                "Leadership",
                "Finance"
            ),
            filterArea
        ) {
            filterArea = it
            filterStatus = ""
        }

        Picker(
            "Industry",
            filterIndustry,
            listOf(
                "All industries",
                "Technology & digital products",
                "Healthcare",
                "Education"
            )
        ) {
            filterIndustry = it
            filterStatus = ""
        }

        Heading("Skills", 18)

        Chips(
            listOf("Any", "Roadmapping", "Communication", "Leadership"),
            filterSkills
        ) {
            filterSkills = it
            filterStatus = ""
        }

        Heading("Availability", 18)

        Chips(
            values = listOf(
                "Any time",
                "This week",
                "Next week",
                "Weekends"
            ),
            selected = filterAvailability
        ) {
            filterAvailability = it
            filterStatus = ""
        }

        Heading("Minimum rating", 18)

        Chips(
            listOf("Any", "4.0+", "4.5+", "4.8+"),
            filterRating
        ) {
            filterRating = it
            filterStatus = ""
        }

        val summary = when {
            filterStatus == "reset" ->
                "Filters reset · All mentors"

            !hasActiveMentorFilters ->
                "No filters selected · All mentors"

            filterStatus == "applied" ->
                "Filters applied · $matchCount mentors"

            else ->
                "$matchCount mentors match your preferences"
        }

        Info(
            summary,
            "You can always adjust these filters to discover more perspectives."
        )

        Action(
            if (hasActiveMentorFilters)
                "Apply filters · $matchCount mentors"
            else
                "Show all mentors"
        ) {
            if (hasActiveMentorFilters) {
                filterStatus = "applied"
            } else {
                resetMentorFilters()
                back()
            }
        }

        Action("Reset filters", secondary = true) {
            resetMentorFilters()
            filterStatus = "reset"
        }

        if (filterStatus == "applied" && hasActiveMentorFilters) {
            Action("View matching mentors", secondary = true) {
                back()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun FilterScreenPreview() {
    JeelWorkspace("Mentee", "", "", initialRoute = "filters") {}
}