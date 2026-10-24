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
internal fun ColumnScope.RequestsScreen(state:JeelUiState) {
    with(state){
        PageHead("MENTORSHIP REQUESTS","Someone's next step")
        BodyText("Take a moment to see where your experience can help.")
        Segments(listOf("Pending","Accepted","Declined"),requestTab){
            requestTab=it
        }
        Heading(if(requestTab=="Pending")"Awaiting your response" else "$requestTab requests",18)
        var visible=0
        listOf("Aisha Rahman","Yusuf Ali").forEachIndexed{
            i,p->if(requestStates[i]==requestTab){
                visible++
                Panel{
                    Section("Received 15 Oct","• ${requestStates[i]}"){
                    }
                    Person(p,if(i==0)"Final-year Computer Science student" else "Business graduate",if(i==0)R.drawable.jeel_aisha else R.drawable.jeel_yusuf)
                    BodyText(if(i==0)"Career roadmap" else "Leadership foundations")
                    Caption(if(i==0)Goal else "Build confidence for my first consulting role")
                    Caption("Sun, ${if(i==0)18 else 25} Oct 2026 · 10:00–10:45 AM")
                    Action("View request ↗",secondary=true){
                        requestPerson=p
                        go("request")
                    }
                }
            }
        }
        if(visible==0)Info("No ${requestTab.lowercase()} requests","Requests with this status will appear here.")
        Info("A thoughtful reply matters","Please respond within 48 hours. Accepted requests appear in your upcoming sessions.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun RequestsScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="requests"){}
}
