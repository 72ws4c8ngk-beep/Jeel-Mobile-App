package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MenteeHomeScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL · MENTEE","Your next chapter",trailing={
            TextButton(onClick={
                go("notifications")
            }){
                JeelOutlineIcon("notifications")
            }
        })
        Row(verticalAlignment=Alignment.CenterVertically){
            Column(Modifier.weight(1f)){
                Heading("Good morning, ${name.substringBefore(' ')}",23)
                Caption("Small steps. Meaningful progress.")
            }
            Avatar(photo,44)
        }
        Panel(Navy){
            Text("BUILT ON REAL EXPERIENCE",fontSize=10.sp,color=Color.White.copy(alpha=.7f))
            Text("You don't have to figure it all out alone.",fontFamily=FontFamily.Serif,color=Color.White,fontSize=25.sp,lineHeight=30.sp)
            Text("Meet mentors who understand your ambition.",color=Color.White.copy(alpha=.7f),fontSize=12.sp)
        }
        Panel{
            Section("Ask Jeel","• New"){
            }
            BodyText("Tell us what you're facing and Jeel will help you find the right mentor.")
            Action("Find my match"){
                go("match")
            }
        }
        Section("Recommended for you","See all"){
            tab("explore")
        }
        MentorCard(mentors[0]){
            openMentor(0)
        }
        Heading("Explore by expertise",18)
        Chips(listOf("Product & tech","Business","Design","Data & analytics"),""){
            filterArea=when(it){
                "Product & tech"->"Product"
                "Business"->"Leadership"
                else->it
            }
            tab("explore")
        }
        Section("Your upcoming session","View all"){
            tab("sessions")
        }
        SessionCard(mentors[0].name,mentors[0].job,"Career roadmap",if(cancelled)"Cancelled" else "Upcoming",mentors[0].image,"$sessionDate · $sessionTime · 45 min"){
            mentorIndex=0
            session(mentors[0].name,"Career roadmap")
        }
        Heading("Inspired by your interests",18)
        MentorCard(mentors[1]){
            openMentor(1)
        }
        Caption("Matched to your interests in technology and product management.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MenteeHomeScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="home"){}
}
