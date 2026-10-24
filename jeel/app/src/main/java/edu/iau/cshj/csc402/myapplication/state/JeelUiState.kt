package edu.iau.cshj.csc402.myapplication.state

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*


@Stable
internal class JeelUiState(
initialRole:String,
initialName:String,
initialEmail:String,
initialRoute:String="home",
initialProfile:Map<String,String> = emptyMap()
) {
    var role by mutableStateOf(initialRole)
    val isMentor get() = role == "Mentor"
    var route by mutableStateOf(initialRoute)
    var history by mutableStateOf(listOf<String>())
    var mentorIndex by mutableIntStateOf(0)
    var requestPerson by mutableStateOf("Aisha Rahman")
    var requestStates by mutableStateOf(listOf("Pending","Pending"))
    var sessionTab by mutableStateOf("Upcoming")
    var requestTab by mutableStateOf("Pending")
    var currentSession by mutableStateOf("Career roadmap")
    var sessionPerson by mutableStateOf("")
    var cancelled by mutableStateOf(false)
    var mentorCompleted by mutableStateOf(false)
    var bookingPending by mutableStateOf(false)
    var savedMentors by mutableStateOf(listOf<Int>())
    var name by mutableStateOf(initialName.ifBlank {
        if(isMentor) "Dr. Omar Haddad" else "Aisha Rahman"
    })
    var email by mutableStateOf(initialEmail.ifBlank {
        if(isMentor) "omar.haddad@gmail.com" else "aisha.rahman@gmail.com"
    })
    var location by mutableStateOf("Riyadh, Saudi Arabia")
    var study by mutableStateOf(initialProfile["study"] ?: "Computer Science")
    var university by mutableStateOf(initialProfile["university"] ?: "King Saud University")
    var careerGoal by mutableStateOf(initialProfile["goal"] ?: Goal)
    var bio by mutableStateOf(initialProfile["bio"] ?: Bio)
    var industry by mutableStateOf(initialProfile["industry"] ?: "Fintech")
    var professionalTitle by mutableStateOf(initialProfile["job"] ?: "Retired Product Director")
    var years by mutableStateOf(initialProfile["years"]?.removeSuffix(" years") ?: "28")
    var interests by mutableStateOf(initialProfile["interests"]?.split("|") ?: listOf("Product management","Technology"))
    var skills by mutableStateOf(initialProfile["skills"]?.split("|") ?: listOf("Product thinking","Interview skills"))
    var topics by mutableStateOf(initialProfile["topics"]?.split("|") ?: listOf("Career roadmap","CV review"))
    var expertise by mutableStateOf(initialProfile["skills"]?.split("|") ?: listOf("Product strategy","Leadership","Roadmapping"))
    var filterArea by mutableStateOf("All expertise")
    var filterIndustry by mutableStateOf("All industries")
    var filterYears by mutableStateOf("Any")
    var filterAvailability by mutableStateOf("Any time")
    var filterRating by mutableStateOf("Any")
    var query by mutableStateOf("")
    var date by mutableIntStateOf(18)
    var month by mutableIntStateOf(10)
    var year by mutableIntStateOf(2026)
    var time by mutableStateOf("10:00 AM")
    var bookingTopic by mutableStateOf("Career roadmap")
    var bookingNote by mutableStateOf(Note)
    var rescheduling by mutableStateOf(initialRoute=="reschedule")
    var sessionDate by mutableStateOf("Sun, 18 Oct 2026")
    var sessionTime by mutableStateOf("10:00 AM")
    var sessionNotes by mutableStateOf("Discuss the transition from CS to product. Review associate PM skill requirements and agree on two practical next steps.")
    var actionTitles by mutableStateOf(listOf("Write a product teardown","Update CV"))
    var actionDueDates by mutableStateOf(listOf("22 Oct","24 Oct"))
    var actionShared by mutableStateOf(listOf(true,true))
    var availableDays by mutableStateOf(initialProfile["days"]?.split("|") ?: listOf("Sun"))
    var selectedDay by mutableStateOf("Sun")
    var slots by mutableStateOf(listOf("11:00 AM","2:00 PM"))
    var slotDialog by mutableStateOf(false)
    var editSlot by mutableIntStateOf(-1)
    var slotText by mutableStateOf("3:00 PM")
    var addGoalDialog by mutableStateOf(false)
    var newGoal by mutableStateOf("")
    var extraGoals by mutableStateOf(listOf<String>())
    var completedActions by mutableStateOf(listOf(0))
    var rating by mutableIntStateOf(0)
    var feedback by mutableStateOf("Omar helped me map the skills I need for an associate PM role. His practical advice gave me a clear plan and much more confidence.")
    var reviewSent by mutableStateOf(false)
    var allRead by mutableStateOf(false)
    var reminders by mutableStateOf(true)
    var updates by mutableStateOf(true)
    var shareProfile by mutableStateOf(true)
    var language by mutableStateOf("English")
    var editSnapshot by mutableStateOf(listOf<String>())
    var photo by mutableIntStateOf(initialProfile["photo"]?.toIntOrNull() ?: if(isMentor)R.drawable.jeel_omar else R.drawable.jeel_aisha)
    var photoOptions by mutableStateOf(false)
    var notice by mutableStateOf("")
    var confirmation by mutableStateOf("")
    val selectedMentor get()=mentors[mentorIndex]
    val requesterIndex get()=if(requestPerson=="Aisha Rahman")0 else 1
    val roots=listOf("home","explore","sessions","progress","profile","requests")
    fun go(next:String){
        if(next=="editProfile")editSnapshot=listOf(name,email,location,study,university,careerGoal,bio,professionalTitle,years,interests.joinToString("|"),skills.joinToString("|"),topics.joinToString("|"),expertise.joinToString("|"),photo.toString(),industry)
        history=history+route
        route=next
        notice=""
    }
    fun tab(next:String){
        history=emptyList()
        route=next
        notice=""
    }
    fun back(){
        if(route=="editProfile"&&editSnapshot.size==15){
            name=editSnapshot[0]
            email=editSnapshot[1]
            location=editSnapshot[2]
            study=editSnapshot[3]
            university=editSnapshot[4]
            careerGoal=editSnapshot[5]
            bio=editSnapshot[6]
            professionalTitle=editSnapshot[7]
            years=editSnapshot[8]
            interests=editSnapshot[9].split("|")
            skills=editSnapshot[10].split("|")
            topics=editSnapshot[11].split("|")
            expertise=editSnapshot[12].split("|")
            photo=editSnapshot[13].toInt()
            industry=editSnapshot[14]
            editSnapshot=emptyList()
        }
        if(history.isNotEmpty()){
            route=history.last()
            history=history.dropLast(1)
        }
        else route="home"
        notice=""
    }
    fun openMentor(index:Int){
        mentorIndex=index
        go("mentor")
    }
    fun session(person:String,topic:String){
        sessionPerson=person
        currentSession=topic
        val index=mentors.indexOfFirst{
            it.name==person
        }
        if(index>=0)mentorIndex=index
        go("session")
    }
    fun filtered():List<IndexedValue<Mentor>> = mentors.withIndex().toList()
}
