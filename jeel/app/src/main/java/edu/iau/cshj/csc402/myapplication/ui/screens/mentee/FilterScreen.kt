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
        Heading("Years of experience",18)
        Chips(listOf("Any","5–10 years","10–20 years","20+ years"),filterYears){
            filterYears=it
        }
        Heading("Availability",18)
        Chips(listOf("Any time","This week","Next week","Weekends"),filterAvailability){
            filterAvailability=it
        }
        Heading("Minimum rating",18)
        Chips(listOf("Any","4.0+","4.5+","4.8+"),filterRating){
            filterRating=it
        }
        Info("${filtered().size} mentors match your preferences","You can always adjust these filters to discover more perspectives.")
        Action("Apply filters · ${filtered().size} mentors"){
            back()
        }
        Action("Reset filters",secondary=true){
            filterArea="All expertise"
            filterYears="Any"
            filterAvailability="Any time"
            filterRating="Any"
            filterIndustry="All industries"
            query=""
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun FilterScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="filters"){}
}
