package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

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
internal fun ColumnScope.MentorEditProfileScreen(state:JeelUiState) {
    val draft=remember { JeelUiState(state.role,"","") }
    with(draft){
        PageHead("EDIT PROFILE · MENTOR","Your experience, their possibility",state::back)
        ProfileHero(name,professionalTitle,photo,"Editing your ${role.lowercase()} profile")
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
        Heading("Professional background",18)
        EditBox("Professional title",professionalTitle){
            professionalTitle=it
        }
        Picker("Industry",industry,listOf("Fintech","Technology","Education","Healthcare")){
            industry=it
        }
        EditBox("Years of experience",years){
            years=it.filter(Char::isDigit)
        }
        EditBox("Professional bio",bio,5){
            bio=it
        }
        TagEditor("Expertise & skills","Add skill",listOf("Product strategy","Leadership","Roadmapping","Stakeholder communication","UX & design","Data & analytics"),expertise){
            expertise=it
        }
        TagEditor("Mentorship topics","Add topic",listOf("Career roadmap","CV review","Leadership","Interview preparation"),topics){
            topics=it
        }
        Picker("Session language","English",listOf("English")){
        }
        Caption("Online mentorship · 45 min sessions")
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
private fun MentorEditProfileScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="editProfile"){}
}
