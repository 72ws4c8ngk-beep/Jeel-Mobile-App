package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeEditProfileScreen(state:JeelUiState) {
    val draft=remember { JeelUiState(state.role,"","") }
    with(draft){
        PageHead("EDIT PROFILE · MENTEE","Keep your story current",state::back)
        ProfileHero(name,study,photo,"Editing your ${role.lowercase()} profile")
        TextButton(onClick={
            photoOptions=!photoOptions
        }){
            Text("Change profile photo",color=Gold)
        }
        if(photoOptions)Row(horizontalArrangement=Arrangement.spacedBy(12.dp)){
            listOf(R.drawable.jeel_aisha,R.drawable.jeel_omar,R.drawable.jeel_leila).forEach{
                image->Box(Modifier.clickable{
                    photo=image
                    photoOptions=false
                }){
                    Avatar(image,48)
                }
            }
        }
        Heading("Personal information",18)
        EditBox("Full name",name){
            name=it
        }
        EditBox("Email",email){
            email=it
        }
        EditBox("Location",location){
            location=it
        }
        Heading("Your study & interests",18)
        EditBox("Field of study",study){
            study=it
        }
        EditBox("University",university){
            university=it
        }
        TagEditor("Your interests","Add interest",listOf("Product management","Technology","Design","Business","Data"),interests){
            interests=it
        }
        EditBox("Career goal",careerGoal,3){
            careerGoal=it
        }
        TagEditor("Skills you want to improve","Add skill",listOf("Product thinking","Interview skills","Communication","Leadership"),skills){
            skills=it
        }
        TagEditor("Preferred mentorship topics","Add topic",listOf("Career roadmap","CV review","Interview preparation","Portfolio review"),topics){
            topics=it
        }
        Info("A profile that grows with you","Keep your interests, skills and goals current so your mentor can guide your next step.")
        Action("Save changes"){
            state.back()
        }
        Action("Cancel",secondary=true){
            state.back()
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeEditProfileScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="editProfile"){}
}
