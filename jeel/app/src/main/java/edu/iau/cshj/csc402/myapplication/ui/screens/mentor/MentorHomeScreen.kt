package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MentorHomeScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL · MENTOR","Experience that matters",trailing={
            TextButton(onClick={
                go("notifications")
            }){
                JeelOutlineIcon("notifications")
            }
        })
        Row(verticalAlignment=Alignment.CenterVertically){
            Column(Modifier.weight(1f)){
                Heading("Welcome back, Omar",23)
                Caption("Your guidance is making a difference.")
            }
            Avatar(photo,44)
        }
        Panel(Navy){
            Text("YOUR EXPERIENCE. THEIR POSSIBILITY.",fontSize=10.sp,color=Color.White.copy(alpha=.7f))
            Text("A lifetime of learning.\nA new generation of growth.",fontFamily=FontFamily.Serif,color=Color.White,fontSize=25.sp,lineHeight=30.sp)
            
        }
        Stats(listOf("112" to "Sessions guided","4.9" to "Avg. rating",requestStates.count{
            it=="Pending"
        }
        .toString() to "Pending"))
        Section("Upcoming session","View all"){
            tab("sessions")
        }
        SessionCard("Yusuf Ali","Business graduate","Interview preparation","Upcoming",R.drawable.jeel_yusuf,"Fri, 16 Oct 2026 · 11:00–11:45 AM"){
            session("Yusuf Ali","Interview preparation")
        }
        Section("Pending requests · ${requestStates.count{it=="Pending"}}","Review all"){
            tab("requests")
        }
        if(requestStates[0]=="Pending")Panel{
            Person("Aisha Rahman","Final-year Computer Science student",R.drawable.jeel_aisha)
            Badge("Pending")
            BodyText("Career roadmap · Sun, 18 Oct 2026 · 10:00–10:45 AM")
            Caption("Aisha is ready to explore her first step into product management.")
            Action("Review request",secondary=true){
                requestPerson="Aisha Rahman"
                go("request")
            }
        } else Info("You're all caught up","View your Requests tab to review accepted and declined requests.")
        Heading("Quick actions",18)
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){
            Box(Modifier.weight(1f)){
                Action("Availability",secondary=true){
                    go("availability")
                }
            }
            Box(Modifier.weight(1f)){
                Action("Edit profile",secondary=true){
                    go("editProfile")
                }
            }
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorHomeScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="home"){}
}
