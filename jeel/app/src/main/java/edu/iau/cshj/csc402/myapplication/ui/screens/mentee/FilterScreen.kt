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
internal fun ColumnScope.FilterScreen(state:JeelUiState) {
    with(state){
        PageHead("EXPLORE · FILTERS","Refine your search",::back)
        BodyText("Find the kind of experience you need.")
        Heading("Expertise",18)
        Chips(listOf("All expertise","Product strategy","UX & design","Data & analytics","Leadership","Finance"),filterArea){
            filterArea=it
        }
        Picker("Industry",filterIndustry,listOf("All industries","Technology & digital products","Healthcare","Education")){
            filterIndustry=it
        }
        Heading("Skills",18)
        Chips(listOf("Any","Roadmapping","Communication","Leadership"),filterSkills){
            filterSkills=it
        }
        Heading("Availability", 18)
        BodyText("Availability filters will be enabled when mentor schedules are connected.")

        Heading("Minimum rating",18)
        Chips(listOf("Any","4.0+","4.5+","4.8+"),filterRating){
            filterRating=it
        }
        Info("${filteredRoomMentors().size} mentors match your preferences","You can always adjust these filters to discover more perspectives.")
        Action("Apply filters · ${filteredRoomMentors().size} mentors"){
            back()
        }
        Action("Reset filters",secondary=true){
            filterArea="All expertise"
            filterSkills="Any"
            filterAvailability="Any time"
            filterRating="Any"
            filterIndustry="All industries"
            query=""
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun FilterScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="filters"){}
}
