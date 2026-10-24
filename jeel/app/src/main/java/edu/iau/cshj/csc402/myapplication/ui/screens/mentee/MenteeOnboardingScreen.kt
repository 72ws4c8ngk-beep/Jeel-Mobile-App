package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelOnboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun MenteeOnboardingScreen(fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    val role="Mentee"
    var study by rememberSaveable{
        mutableStateOf("Computer Science")
    }
    var university by rememberSaveable{
        mutableStateOf("King Saud University")
    }

    var description by rememberSaveable{
        mutableStateOf("Final-year student at King Saud University. Curious about building products that solve real problems.")
    }
    var goal by rememberSaveable{
        mutableStateOf(Goal)
    }
    var interests by rememberSaveable{
        mutableStateOf(listOf("Product management","Technology"))
    }
    var skills by rememberSaveable{
        mutableStateOf(listOf("Product thinking","Interview skills"))
    }
    var topics by rememberSaveable{
        mutableStateOf(listOf("Career roadmap","CV review"))
    }

    var photo by rememberSaveable{
        mutableIntStateOf(R.drawable.jeel_aisha)
    }
    var photoOptions by rememberSaveable{
        mutableStateOf(false)
    }
    BackHandler{
        back()
    }
    Surface(color=Cream,modifier=Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(18.dp)) {
            PageHead("${role.uppercase()} · STEP 2 OF 2","Let's get to know you",back)
            BodyText("Tell us where you are today. We'll help you find the right experience for what comes next.")
            Column(Modifier.fillMaxWidth(),horizontalAlignment=Alignment.CenterHorizontally) {
                Avatar(photo,76)
                TextButton(onClick={
                    photoOptions=!photoOptions
                }){
                    Text("Change profile photo",color=Gold,fontSize=12.sp)
                }
            }
            if(photoOptions)Row(horizontalArrangement=Arrangement.spacedBy(16.dp)){
                listOf(R.drawable.jeel_aisha,R.drawable.jeel_omar,R.drawable.jeel_leila).forEach{
                    image->Box(Modifier.clickable{
                        photo=image
                        photoOptions=false
                    }){
                        Avatar(image,52)
                    }
                }
            }
            Picker("Field of study",study,listOf("Computer Science","Business","Design","Engineering","Other")){
                study=it
            }
            Picker("University",university,listOf("King Saud University","Imam Abdulrahman Bin Faisal University","Other")){
                university=it
            }
            Heading("Your interests",18)
            MultiChips(listOf("Product management","Technology","UX design","Entrepreneurship"),interests){
                interests=it
            }
            EditBox("Career goal",goal,3){
                goal=it
            }
            Heading("Skills to improve",18)
            MultiChips(listOf("Product thinking","Interview skills","Communication","Leadership"),skills){
                skills=it
            }
            Heading("Preferred mentorship topics",18)
            MultiChips(listOf("Career roadmap","CV review","Interview preparation","Portfolio review","Networking","Leadership"),topics){
                topics=it
            }
            EditBox("A little about you · optional",description,3){
                description=it
            }
            Action("→ Save and continue"){
                complete(emptyMap())
            }
            Caption("You can refine your profile whenever your goals evolve.")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeOnboardingScreenPreview() {
    JeelOnboarding("Mentee","",{},{})
}
