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
internal fun ColumnScope.MenteeProfileScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL · MENTEE","Your profile")
        ProfileHero(name,"Final-year $study student",photo,"Mentee")
        Action("✎ Edit profile",secondary=true){
            go("editProfile")
        }
        Heading("Personal information",18)
        Panel{
            Detail("✉","Email",email)
            Detail("◇","University",university)
            Detail("◎","Location",location)
        }
        Heading("Your interests",18)
        Chips(interests,""){
        }
        Heading("Your career goal",18)
        Info(careerGoal,"Focus skills: ${skills.joinToString()}")
        Heading("Preferred topics",18)
        Chips(topics,""){
        }
        if(savedMentors.isNotEmpty()){
            Heading("Saved mentors",18)
            savedMentors.forEach{
                i->MentorCard(mentors[i]){
                    openMentor(i)
                }
            }
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
private fun MenteeProfileScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="profile"){}
}
