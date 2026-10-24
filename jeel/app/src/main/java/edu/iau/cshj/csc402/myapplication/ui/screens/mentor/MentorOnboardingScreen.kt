package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

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
internal fun MentorOnboardingScreen(fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    val role="Mentor"

    var job by rememberSaveable{
        mutableStateOf("Retired Product Director")
    }
    var industry by rememberSaveable{
        mutableStateOf("Fintech")
    }
    var experience by rememberSaveable{
        mutableStateOf("28 years")
    }
    var description by rememberSaveable{
        mutableStateOf(Bio)
    }

    var skills by rememberSaveable{
        mutableStateOf(listOf("Product strategy","Leadership"))
    }
    var topics by rememberSaveable{
        mutableStateOf(listOf("Career roadmap","CV review"))
    }
    var days by rememberSaveable{
        mutableStateOf(listOf("Sun"))
    }
    var times by rememberSaveable{
        mutableStateOf("10:00 AM, 11:00 AM, 2:00 PM")
    }
    var language by rememberSaveable{
        mutableStateOf("English")
    }
    var photo by rememberSaveable{
        mutableIntStateOf(R.drawable.jeel_omar)
    }
    var photoOptions by rememberSaveable{
        mutableStateOf(false)
    }
    BackHandler{
        back()
    }
    Surface(color=Cream,modifier=Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(18.dp)) {
            PageHead("${role.uppercase()} · STEP 2 OF 2","Share your experience",back)
            BodyText("Your experience can become someone else's turning point. Help mentees get to know you.")
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
            EditBox("Job title",job){
                job=it
            }
            Picker("Expertise","Product strategy",listOf("Product strategy","UX & design","Data & analytics")){
                skills=listOf(it)
            }
            Picker("Industry",industry,listOf("Fintech","Technology","Education","Healthcare")){
                industry=it
            }
            Picker("Years of experience",experience,listOf("5–10 years","10–20 years","20+ years","28 years")){
                experience=it
            }
            EditBox("Professional bio",description,4){
                description=it
            }
            Heading("Your skills",18)
            MultiChips(listOf("Product strategy","Leadership","Roadmapping","Stakeholder communication"),skills){
                skills=it
            }
            Heading("Mentorship topics",18)
            MultiChips(listOf("Career roadmap","CV review","Interview preparation","Portfolio review","Networking","Leadership"),topics){
                topics=it
            }
            
                Heading("Your availability",18)
                MultiChips(listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun"),days){
                    days=it
                }
                EditBox("Available time slots",times){
                    times=it
                }
                Picker("Session language",language,listOf("English","العربية","Both")){
                    language=it
                }
                Info("Your time, your pace","Sessions are 45 min. You can adjust your schedule or pause availability at any time.")
            
            Action("→ Save and continue"){
                complete(emptyMap())
            }
            Caption("You can refine your profile whenever your goals evolve.")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorOnboardingScreenPreview() {
    JeelOnboarding("Mentor","",{},{})
}
