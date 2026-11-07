package edu.iau.cshj.csc402.myapplication.ui.screens.shared
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R

import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
internal fun ProfileOnboardingScreen(role:String,fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    if(role=="Mentor") MentorOnboardingScreen(fullName,back,complete)
    else MenteeOnboardingScreen(fullName,back,complete)
}
@Composable
private fun MenteeOnboardingScreen(fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    val role="Mentee"
    var age by rememberSaveable { mutableStateOf("22") }
    var studyStage by rememberSaveable { mutableStateOf("Final-year undergraduate") }
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
        Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
            UiStatePresentation {
            PageHead("PROFILE ONBOARDING · ${role.uppercase()}","Let's get to know you",back)
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
            EditBox("Age",age) { age=it }
            Picker("Study stage",studyStage,listOf("Undergraduate","Final-year undergraduate","Graduate","Postgraduate")) { studyStage=it }
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
                Action("→ Save and continue") {
                    complete(
                        mapOf(
                            "fullName" to fullName,
                            "role" to role,
                            "age" to age,
                            "studyStage" to studyStage,
                            "fieldOfStudy" to study,
                            "university" to university,
                            "description" to description,
                            "goal" to goal,
                            "interests" to interests.joinToString(", "),
                            "skills" to skills.joinToString(", "),
                            "topics" to topics.joinToString(", "),
                            "photo" to photo.toString()
                        )
                    )
                }
            Caption("You can edit these details later from Edit Profile.")
            }
        }
    }
}


@Composable
private fun MentorOnboardingScreen(fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    val role="Mentor"
    var age by rememberSaveable { mutableStateOf("58") }

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
        Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
            UiStatePresentation {
            PageHead("PROFILE ONBOARDING · ${role.uppercase()}","Share your experience",back)
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
            EditBox("Age",age) { age=it }
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

                Action("→ Save and continue") {
                    complete(
                        mapOf(
                            "fullName" to fullName,
                            "role" to role,
                            "age" to age,
                            "jobTitle" to job,
                            "industry" to industry,
                            "experience" to experience,
                            "description" to description,
                            "skills" to skills.joinToString(", "),
                            "topics" to topics.joinToString(", "),
                            "days" to days.joinToString(", "),
                            "times" to times,
                            "language" to language,
                            "photo" to photo.toString()
                        )
                    )
                }
            Caption("You can edit these details later from Edit Profile.")
            }
        }
    }
}
