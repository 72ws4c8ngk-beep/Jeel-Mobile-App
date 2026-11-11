package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*
import java.util.Calendar
@Composable
internal fun ColumnScope.BookingScreen(state:JeelUiState, submitBooking: () -> Unit) {
    with(state){
        PageHead(if(rescheduling) "RESCHEDULE YOUR SESSION" else "BOOK A SESSION",if(rescheduling) "Choose a new time" else "Make time for growth",::back)
        if(rescheduling) Info("Current appointment",sessionDate+" · "+sessionTime+" · 45 min on Zoom")
        Panel{
            Person(selectedMentor.name,selectedMentor.job,selectedMentor.image)
            Caption("45 min on Zoom · English")
        }
        Heading("Choose a date",18)
        CalendarCard(year,month,date,{
            date=it
            time=""
        },{
            y,m->year=y
            month=m
            date=1
            time=""
        })
        Heading("Available times · $date ${monthName(month)}",18)
        val calendar = Calendar.getInstance().apply {
            set(year, month - 1, date)
        }

        val selectedWeekday = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            else -> "Sun"
        }

        val availableTimes = bookingAvailabilitySlots
            .filter { it.dayOfWeek == selectedWeekday }
            .map { it.startTime }

        if (availableTimes.isEmpty()) {
            Info("No available times", "This mentor has no available slots on this day.")
        } else {
            Chips(availableTimes, time) {
                time = it
            }
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
        if (notice.isNotBlank()) Info("Booking update", notice)
        if (availableTimes.isNotEmpty() && time in availableTimes) {
            Action(if(rescheduling) "Confirm reschedule" else "Confirm booking", enabled = !savingSession) {
                if (useRoomSessions) submitBooking() else go("confirmation")
            }
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun BookingScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="book"){}
}
