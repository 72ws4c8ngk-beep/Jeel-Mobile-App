package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.ProgressScreen(state:JeelUiState) {
    with(state){
        PageHead("YOUR PROGRESS · UPDATED 18 OCT","Look how far you've come")
        Panel(Mist){
            Text("YOUR CAREER GOAL",color=Gold,fontSize=11.sp)
            Heading("Become an associate product manager.",23)
            BodyText("Build the skills and confidence to land your first product role after graduation.")
            Stats(listOf("60%" to "Overall progress","3" to "Sessions completed"))
        }
        Section("Your learning goals","Add goal"){
            addGoalDialog=true
        }
        ProgressCard("Build product thinking",.75f,"3 of 4 steps · Next: write a product teardown")
        ProgressCard("Prepare for PM interviews",.4f,"2 of 5 steps · Next: practice a product case")
        extraGoals.forEach{
            ProgressCard(it,0f,"New goal · Not started")
        }
        Heading("From your session",18)
        Panel{
            listOf("Identify 5 associate PM roles","Write a product teardown","Update CV").forEachIndexed{
                i,t->Row(verticalAlignment=Alignment.CenterVertically){
                    Checkbox(i in completedActions,{
                    })
                    Column{
                        BodyText(t)
                        Caption("Due ${20+i*2} Oct · ${if(i in completedActions)"Completed" else "Not started"}")
                    }
                }
            }
        }
        Heading("Your journey",18)
        Panel{
            listOf("18 Oct 2026" to "A clearer career roadmap","4 Oct 2026" to "A strong first impression","27 Sep 2026" to "Found your product direction").forEach{
                (d,t)->Text(d,color=Gold,fontSize=12.sp)
                BodyText("✓ $t")
                Caption("Mentorship · Completed")
            }
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun ProgressScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="progress"){}
}
