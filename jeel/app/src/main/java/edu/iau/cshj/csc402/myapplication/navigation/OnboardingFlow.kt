package edu.iau.cshj.csc402.myapplication.navigation

import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

import edu.iau.cshj.csc402.myapplication.ui.screens.mentee.*
import edu.iau.cshj.csc402.myapplication.ui.screens.mentor.*
import edu.iau.cshj.csc402.myapplication.ui.screens.shared.*

@Composable
fun JeelOnboarding(role:String,fullName:String,back:()->Unit,complete:(Map<String,String>)->Unit) {
    JeelTheme {
        if(role=="Mentor") MentorOnboardingScreen(fullName,back,complete)
        else MenteeOnboardingScreen(fullName,back,complete)
    }
}
