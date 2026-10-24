package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.FindMatchScreen(state:JeelUiState) {
    with(state){
        PageHead("ASK JEEL","Find your next step",::back)
        BodyText("Tell us what you'd like guidance with.")
        EditBox("Your challenge",bookingNote,3){
            bookingNote=it
        }
        Heading("What matters most?",18)
        Chips(listOf("Career roadmap","CV review","Interview preparation","Portfolio review"),bookingTopic){
            bookingTopic=it
        }
        Info("A thoughtful starting point","This prototype matches you to local sample mentors; it doesn't send your message to an AI service.")
        Action("Find my mentor"){
            query=""
            filterArea=if(bookingTopic=="Portfolio review")"Design" else "Product"
            tab("explore")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun FindMatchScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="match"){}
}
