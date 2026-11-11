package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.components.Action
import edu.iau.cshj.csc402.myapplication.ui.components.Badge
import edu.iau.cshj.csc402.myapplication.ui.components.BodyText
import edu.iau.cshj.csc402.myapplication.ui.components.Caption
import edu.iau.cshj.csc402.myapplication.ui.components.EditBox
import edu.iau.cshj.csc402.myapplication.ui.components.Heading
import edu.iau.cshj.csc402.myapplication.ui.components.PageHead
import edu.iau.cshj.csc402.myapplication.ui.components.Panel
import edu.iau.cshj.csc402.myapplication.ui.components.Person
import edu.iau.cshj.csc402.myapplication.ui.theme.Gold
import edu.iau.cshj.csc402.myapplication.state.JeelUiState

@Composable
internal fun ColumnScope.SessionReviewScreen(state:JeelUiState) {
    with(state){
        PageHead("SESSION COMPLETED","A little reflection",::back)
        Panel{
            Person(sessionPerson,selectedMentor.job,selectedMentor.image)
            Badge("Completed")
            BodyText("Career roadmap")
            Caption("$sessionDate · $sessionTime")
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
        Caption("Your feedback is saved with this session.")
        Action("Submit review",enabled=rating in 1..5 && !sessionSaving && selectedSession?.status == "Completed" && selectedSession?.rating == null){
            submitSelectedReview()
        }
        Action("Not now",secondary=true){
            tab("sessions")
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun SessionReviewScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="review"){}
}
