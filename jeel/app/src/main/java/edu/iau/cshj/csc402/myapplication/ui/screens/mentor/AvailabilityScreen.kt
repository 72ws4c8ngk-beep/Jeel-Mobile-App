package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.AvailabilityScreen(state:JeelUiState) {
    with(state){
        PageHead("MANAGE AVAILABILITY","Make room for mentorship",::back)
        BodyText("Choose a rhythm that works for you. Your availability repeats weekly.")
        Picker("Time zone","Arabia Standard Time (UTC+3)",listOf("Arabia Standard Time (UTC+3)")){
        }
        Heading("Weekly schedule preview",18)
        Panel{
            BodyText("12–18 October 2026")
            Chips(listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun"),selectedDay){
                selectedDay=it
            }
            Caption("$selectedDay selected · ${slots.size} available slots · 1 booked")
        }
        Heading("Available days",18)
        MultiChips(listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun"),availableDays){
            availableDays=it
            selectedDay=it.lastOrNull()?:"Sun"
        }
        Heading("$selectedDay time slots",18)
        if(selectedDay in availableDays){
            if(selectedDay=="Sun")Panel{
                BodyText("10:00–10:45 AM")
                Caption("Booked · locked for 18 Oct")
            }
            slots.forEachIndexed{
                i,s->Panel{
                    Row(verticalAlignment=Alignment.CenterVertically){
                        Column(Modifier.weight(1f)){
                            BodyText(s)
                            Caption("Available · 45 min")
                        }
                        TextButton(onClick={
                            editSlot=i
                            slotText=s
                            slotDialog=true
                        }){
                            Text("Edit")
                        }
                        TextButton(onClick={
                        }){
                            Text("Delete",color=Color(0xFFB36A60))
                        }
                    }
                }
            }
            Action("+ Add new time slot",secondary=true){
                editSlot=-1
                slotText="3:00 PM"
                slotDialog=true
            }
        } else Info("No availability on $selectedDay","Select this day above to add availability.")
        Info("Existing bookings stay in place","Weekly availability changes apply only to new requests. Reschedule or cancel a booked session from its details.")
        Action("Save availability"){
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun AvailabilityScreenPreview() {
    JeelWorkspace("Mentor","","",initialRoute="availability"){}
}
