package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MentorProfileScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL · MENTOR","Your mentor profile")
        ProfileHero(name,"$professionalTitle · $industry",photo,"Verified mentor")
        Action("✎ Edit profile",secondary=true){
            go("editProfile")
        }
        Stats(listOf(years to "Years of experience","4.9" to "64 reviews","112" to "Sessions guided"))
        Heading("Professional bio",18)
        BodyText(bio)
        Heading("Expertise & skills",18)
        Chips(expertise,""){
        }
        Heading("Mentorship topics",18)
        Chips(topics,""){
        }
        Section("Latest review","All 64"){
            mentorIndex=0
            go("reviews")
        }
        ReviewCard()
        Heading("Professional details",18)
        Panel{
            Detail("✉","Email",email)
            Detail("▦","Availability","Sundays · 10 AM, 11 AM & 2 PM")
            Detail("◎","Location",location)
            Detail("◎","Session language","English")
        }
        Action("Manage availability",secondary=true){
            go("availability")
        }
        Action("⚙ Settings",secondary=true){
            go("settings")
        }
        Action("Logout",secondary=true){
            confirmation="logout"
        }
        Caption("Jeel 1.0 · Guidance for every generation")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorProfileScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="profile"){}
}
