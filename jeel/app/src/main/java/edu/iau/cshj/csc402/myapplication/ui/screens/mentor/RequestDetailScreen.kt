package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.RequestDetailScreen(state:JeelUiState) {
    with(state){
        PageHead("REQUESTS · ${requestStates[requesterIndex].uppercase()}","Meet ${requestPerson.substringBefore(' ')}",::back)
        Panel{
            Person(requestPerson,if(requesterIndex==0)"Final-year Computer Science student" else "Business graduate",if(requesterIndex==0)R.drawable.jeel_aisha else R.drawable.jeel_yusuf)
            Badge(requestStates[requesterIndex])
            Detail("◇","Field of study",if(requesterIndex==0)"Computer Science · King Saud University" else "Business administration")
            Detail("◎","Career goal",if(requesterIndex==0)Goal else "Build confidence for my first consulting role")
        }
        Heading("Where they'd like guidance",18)
        Chips(listOf("Product management","Technology","Product thinking"),""){
        }
        Panel(Mist){
            SessionDetails("Sunday, 18 October 2026","10:00 AM",if(requesterIndex==0)"Career roadmap" else "Leadership foundations")
            Detail("▣","Format","Online session · English")
        }
        Heading("A message from ${requestPerson.substringBefore(' ')}",18)
        Panel{
            BodyText(Note)
        }
        Info("Suggested match based on their goal and your expertise.","Their goals align with your career direction and product strategy topics.")
        if(requestStates[requesterIndex]=="Pending"){
            Action("✓ Accept request"){
                tab("sessions")
            }
            DangerButton("Decline request"){
                confirmation="decline"
            }
        } else Action("View sessions",secondary=true){
            tab("sessions")
        }
        Caption("Preview only — no notification is sent.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun RequestDetailScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="request"){}
}
