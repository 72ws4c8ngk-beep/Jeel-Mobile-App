package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.RescheduleScreen(state:JeelUiState) {
    with(state){
        PageHead("RESCHEDULE YOUR SESSION","Choose a new time",::back)
        Panel{
            Person(selectedMentor.name,selectedMentor.job,selectedMentor.image)
            Caption("45 min online mentorship · English")
        }
        Heading("Choose a date",18)
        CalendarCard(year,month,date,{
            date=it
        },{
            y,m->year=y
            month=m
            date=1
        })
        Heading("Available times · $date ${monthName(month)}",18)
        Chips(listOf("10:00 AM","11:00 AM","2:00 PM"),time){
            time=it
        }
        Caption("Each slot is 45 min · Arabia Standard Time (UTC+3)")
        Picker("Mentorship topic",bookingTopic,listOf("Career roadmap","CV review","Interview preparation","Portfolio review","Leadership")){
            bookingTopic=it
        }
        EditBox("A note for ${selectedMentor.name.removePrefix("Dr. ").substringBefore(' ')} · optional",bookingNote,3){
            bookingNote=it
        }
        Info("Your request, thoughtfully considered","Your mentor will review your request. You'll be notified when they accept.")
        Info("Your selection","$date ${monthName(month)} $year · $time · 45 min\n$bookingTopic with ${selectedMentor.name}")
        Action("Request reschedule"){
            go("confirmation")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun RescheduleScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="reschedule"){}
}
