package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MentorDetailScreen(state:JeelUiState) {
    with(state){
        PageHead("EXPLORE · PROFILE","Meet your mentor",::back,trailing={
            TextButton(onClick={
            }){
                Text(if(mentorIndex in savedMentors)"★" else "☆",fontSize=25.sp)
            }
        })
        ProfileHero(selectedMentor.name,selectedMentor.job,selectedMentor.image,"Verified mentor")
        Chips(listOf(selectedMentor.area,"${selectedMentor.years} years of experience"),""){
        }
        Stats(listOf((if(LocalUiScene.current.state=="No reviews") "—" else selectedMentor.rating) to (if(LocalUiScene.current.state=="No reviews") "No reviews yet" else "64 reviews"),"112" to "Sessions guided",selectedMentor.years.toString() to "Years of experience"))
        Heading("About ${selectedMentor.name.removePrefix("Dr. ").substringBefore(' ')}",18)
        BodyText(if(mentorIndex==0)Bio else "I help emerging professionals build confidence, explore their strengths, and turn their ambitions into a practical career plan.")
        BodyText("Expect honest conversations, practical frameworks, and a clear next step after every session.")
        Heading("Skills & expertise",18)
        Chips(selectedMentor.tags+listOf("Roadmapping","Stakeholder communication"),""){
        }
        Heading("Areas of mentorship",18)
        Panel{
            Detail("◎","Career direction","From your current interests to your next career step")
            Detail("▣","Professional growth","CV reviews, interviews and leadership")
        }
        Heading("What mentees say · 64 reviews",18)
        if(LocalUiScene.current.state=="No reviews") Info("No reviews yet","Be the first to share your experience after a session.") else ReviewCard()
        val days = bookingAvailabilitySlots
            .filter { it.mentorId == selectedRoomMentorId }
            .map { it.dayOfWeek }
            .toSet()
        val dates = buildList {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Riyadh"))
            repeat(21) {
                val weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH)
                if (weekday in days && size < 3) {
                    add(calendar.clone() as Calendar)
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        val labels = dates.map { day ->
            val weekday = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH)
            "$weekday, ${day.get(Calendar.DAY_OF_MONTH)} ${monthName(day.get(Calendar.MONTH) + 1)}"
        }
        var selectedLabel by remember(selectedRoomMentorId) { mutableStateOf("") }
        val selectedDate = dates.getOrNull(labels.indexOf(selectedLabel)) ?: dates.firstOrNull()

        Heading("Available dates",18)
        if (dates.isNotEmpty()) {
            Chips(labels, selectedLabel.takeIf { it in labels } ?: labels.first()) {
                selectedLabel = it
            }
        } else {
            Info("No available dates", "This mentor has not added available times yet.")
        }
        Caption("Arabia Standard Time (UTC+3)")
        Action(
            if (selectedDate != null) "Book session" else "No available slots",
            enabled = selectedDate != null
        ) {
            val day = selectedDate ?: return@Action
            rescheduling = false
            go("book")
            year = day.get(Calendar.YEAR)
            month = day.get(Calendar.MONTH) + 1
            date = day.get(Calendar.DAY_OF_MONTH)
            time = ""
        }
    }
}

@Preview(showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun MentorDetailScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="mentor"){}
}
