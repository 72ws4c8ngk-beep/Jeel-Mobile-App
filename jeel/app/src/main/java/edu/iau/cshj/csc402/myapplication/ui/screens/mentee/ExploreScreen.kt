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
internal fun ColumnScope.ExploreScreen(state:JeelUiState) {
    with(state){
        PageHead("EXPLORE","Find your mentor")
        Info("Suggested matches","These mentors match your challenge, goals, and interests.")
        SearchBox(query){
            query=it
        }
        Section("${filtered().size} mentors available","☷ Filter"){
            go("filters")
        }
        Chips(listOf("All expertise","Product","Design","Data"),filterArea){
            filterArea=it
        }
        val results=filtered()
        if(results.isEmpty())Info("No mentors found","Try another search or reset your filters.")
        results.forEach{
            (i,m)->MentorCard(m){
                openMentor(i)
            }
        }
        Caption("Showing mentors who offer sessions in English.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun ExploreScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="explore"){}
}
