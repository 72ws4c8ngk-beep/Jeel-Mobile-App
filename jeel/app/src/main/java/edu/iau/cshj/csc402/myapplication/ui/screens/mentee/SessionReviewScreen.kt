package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.SessionReviewScreen(state:JeelUiState) {
    with(state){
        PageHead("SESSION COMPLETED · 18 OCT","A little reflection",::back)
        Panel{
            Person(mentors[0].name,mentors[0].job,mentors[0].image)
            Badge("Completed")
            BodyText("Career roadmap")
            Caption("18 Oct 2026 · 10:00–10:45 AM")
        }
        Heading("How was your session?",25)
        BodyText("Your feedback helps great guidance reach more people.")
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly){
            (1..5).forEach{
                n->TextButton(onClick={
                    rating=n
                },modifier=Modifier.weight(1f),contentPadding=PaddingValues(0.dp)){
                    Text(if(n<=rating)"★" else "☆",color=Gold,fontSize=32.sp)
                }
            }
        }
        Caption(if(rating==0)"Select a rating" else "$rating out of 5")
        EditBox("Your feedback",feedback,4){
            feedback=it
        }
        Caption("Your review will be visible on your mentor's profile with your first name.")
        Action("Submit review",enabled=true){
            sessionTab="Completed"
            tab("sessions")
        }
        Action("Not now",secondary=true){
            tab("sessions")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun SessionReviewScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="review"){}
}
