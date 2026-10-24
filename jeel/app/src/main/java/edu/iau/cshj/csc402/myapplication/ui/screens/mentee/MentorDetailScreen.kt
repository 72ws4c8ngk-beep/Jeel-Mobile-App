package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
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
        Stats(listOf(selectedMentor.rating to "64 reviews","112" to "Sessions guided",selectedMentor.years.toString() to "Years of experience"))
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
        Section("What mentees say","All 64 reviews"){
            go("reviews")
        }
        ReviewCard()
        Heading("Available dates",18)
        Chips(listOf("Sun, 18 Oct","Sun, 25 Oct","Sun, 1 Nov"),""){
            date=if(it.contains("25"))25 else if(it.contains("1 Nov"))1 else 18
            month=if(it.contains("Nov"))11 else 10
        }
        Caption("45 min online sessions · English · Arabia Standard Time (UTC+3)")
        Action(if(selectedMentor.available)"Book session" else "No available slots",enabled=selectedMentor.available){
            rescheduling=false
            go("book")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorDetailScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="mentor"){}
}
