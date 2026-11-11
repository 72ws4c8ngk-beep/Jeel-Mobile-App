package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.data.toDisplayMentor

@Composable
internal fun ColumnScope.ExploreScreen(state:JeelUiState) {
    with(state){
        PageHead("EXPLORE","Find your mentor")
        Info("Suggested matches","Discover experience that matches your goals and interests.")
        SearchBox(query){
            query=it
        }
        Section("${filteredRoomMentors().size} mentors found","☷ Filter"){
            go("filters")
        }
        Chips(listOf("All expertise","Product","Design","Data"),filterArea){
            filterArea=it
        }
        val results = filteredRoomMentors()

        results.forEach { mentor ->
            MentorCard(mentor.toDisplayMentor()) {
                openRoomMentor(mentor.mentorId)
            }
        }
        Caption("Showing mentors who offer sessions in English.")
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun ExploreScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="explore"){}
}
