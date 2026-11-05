package edu.iau.cshj.csc402.myapplication.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

import edu.iau.cshj.csc402.myapplication.ui.screens.mentee.*
import edu.iau.cshj.csc402.myapplication.ui.screens.mentor.*
import edu.iau.cshj.csc402.myapplication.ui.screens.shared.*

@Composable
fun JeelWorkspace(initialRole:String,initialName:String,initialEmail:String,initialRoute:String="home",initialProfile:Map<String,String> = emptyMap(),logout:()->Unit) {
    val scene=LocalUiScene.current
    val state=remember(initialRole,initialRoute,scene){
        JeelUiState(initialRole,initialName,initialEmail,initialRoute,initialProfile).apply {
            when(scene.state) {
                "Completed tab" -> sessionTab="Completed"
                "Cancelled tab" -> { sessionTab="Cancelled"; cancelled=true }
                "Accepted tab" -> { requestTab="Accepted"; requestStates=listOf("Accepted","Accepted") }
                "Rejected tab" -> { requestTab="Rejected"; requestStates=listOf("Rejected","Rejected") }
                "Accepted" -> requestStates=listOf("Accepted","Pending")
                "Rejected" -> requestStates=listOf("Rejected","Pending")
                "Rating selected" -> rating=5
                "Read" -> allRead=true
                "Arabic selected" -> language="العربية"
                "Notifications off" -> { reminders=false; updates=false }
                "Reschedule","Reschedule confirmed" -> rescheduling=true
                "Awaiting approval" -> bookingPending=true
                "No slot selected" -> { date=0; time="" }
                "Search results" -> query="Product strategy"
                "No filters","Reset" -> { filterArea="All expertise"; filterSkills="Any"; filterAvailability="Any time" }
                "Add slot","Edit slot","Invalid time","Overlapping slots" -> {
                    slotDialog=true; editSlot=if(scene.state=="Edit slot") 0 else -1
                    slotText=if(scene.state=="Invalid time") "11:45 AM" else "11:00 AM"
                }
                "Confirm cancellation" -> confirmation="cancel"
                "Confirm acceptance" -> confirmation="accept"
                "Confirm rejection" -> confirmation="reject"
                "Confirm deletion" -> confirmation="delete"
                "Confirm logout" -> confirmation="logout"
                "Discard changes" -> confirmation="discard"
            }
        }
    }
    with(state){
        BackHandler{
            back()
        }
        JeelTheme {
            Scaffold(containerColor=Cream,contentWindowInsets=WindowInsets.safeDrawing,bottomBar={
                if(route in roots||route in listOf("session","request","notifications")) NavigationBar(containerColor=Color.White,tonalElevation=0.dp){
                    val tabs=if(isMentor)listOf("home" to "Home","requests" to "Requests","sessions" to "Sessions","profile" to "Profile")else listOf("home" to "Home","explore" to "Explore","sessions" to "Sessions","progress" to "Progress","profile" to "Profile")
                    tabs.forEach{
                        (key,label)->NavigationBarItem(selected=route==key||(key=="sessions"&&route=="session")||(key=="requests"&&route=="request"),onClick={
                            tab(key)
                        },icon={
                            JeelOutlineIcon(key)
                        },label={
                            Text(label,fontSize=10.sp)
                        },colors=NavigationBarItemDefaults.colors(indicatorColor=Mist,selectedIconColor=Navy,selectedTextColor=Navy,unselectedIconColor=Slate,unselectedTextColor=Slate))
                    }
                }
            }){
                padding->
                key(route){
                    Column(Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState()).padding(horizontal=24.dp,vertical=16.dp),verticalArrangement=Arrangement.spacedBy(22.dp)){
                        UiStatePresentation {
                        when(route){
                            "home" -> if(isMentor) MentorHomeScreen(state) else MenteeHomeScreen(state)
                            "explore" -> ExploreScreen(state)
                            "filters" -> FilterScreen(state)
                            "mentor" -> MentorDetailScreen(state)
                            "book" -> BookingScreen(state)
                            "confirmation" -> BookingConfirmationScreen(state)
                            "sessions" -> if(isMentor) MentorSessionsScreen(state) else MenteeSessionsScreen(state)
                            "session" -> SessionDetailsScreen(state)
                            "review" -> SessionReviewScreen(state)
                            "requests" -> RequestsScreen(state)
                            "request" -> RequestDetailScreen(state)
                            "availability" -> AvailabilityScreen(state)
                            "progress" -> ProgressScreen(state)
                            "profile" -> MyProfileScreen(state)
                            "editProfile" -> if(isMentor) MentorEditProfileScreen(state) else MenteeEditProfileScreen(state)
                            "notifications" -> NotificationsScreen(state)
                            "settings" -> SettingsScreen(state)
                        }
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
            if(confirmation.isNotEmpty())AlertDialog(onDismissRequest={
                confirmation=""
            },title={
                Text(when(confirmation){
                    "logout"->"Log out of Jeel?"
                    "cancel"->"Cancel this session?"
                    "accept"->"Accept this request?"
                    "reject"->"Reject this request?"
                    "delete"->"Delete this time slot?"
                    "discard"->"Discard your changes?"
                    else->"Confirm this action?"
                })
            },text={
                Text(when(confirmation) {
                    "logout" -> "You can log in again at any time to continue your mentorship journey."
                    "cancel" -> "Both participants will be notified. This appointment will leave your upcoming sessions."
                    "accept" -> "An upcoming Zoom session will be created for this mentorship."
                    "reject" -> "The mentee will be notified of your decision."
                    "delete" -> "Mentees will no longer be able to book this available time. Existing bookings stay unchanged."
                    "discard" -> "Your unsaved edits will be lost. Your saved profile will stay as it was."
                    else -> "Review the details before continuing."
                })
            },confirmButton={
                TextButton(onClick={
                    when(confirmation){
                        "logout" -> logout()
                        "cancel" -> tab("sessions")
                        "reject" -> tab("requests")
                        "accept" -> tab("sessions")
                        "discard" -> back()
                    }
                    confirmation=""
                }){
                    Text(when(confirmation) {
                        "logout" -> "Log out"
                        "cancel" -> "Cancel session"
                        "accept" -> "Accept request"
                        "reject" -> "Reject request"
                        "delete" -> "Delete slot"
                        "discard" -> "Discard changes"
                        else -> "Confirm"
                    })
                }
            },dismissButton={
                TextButton(onClick={
                    confirmation=""
                }){
                    Text(if(confirmation=="discard") "Keep editing" else "Go back")
                }
            })
            if(slotDialog)AlertDialog(onDismissRequest={
                slotDialog=false
            },title={
                Text(if(editSlot<0)"Add time slot" else "Edit time slot")
            },text={
                Column(verticalArrangement=Arrangement.spacedBy(12.dp)) {
                    Picker("Day",selectedDay,listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")) { selectedDay=it }
                    EditBox("Start time",slotText) { slotText=it }
                    EditBox("End time",if(scene.state=="Invalid time") "11:00 AM" else "11:45 AM") { }
                    Caption("Arabia Standard Time (UTC+3)")
                    if(scene.state=="Invalid time") Info("Invalid time","End time must be after start time.")
                    if(scene.state=="Overlapping slots") Info("Overlapping slots","This time overlaps 11:00–11:45 AM. Choose another slot.")
                }
            },confirmButton={
                TextButton(onClick={
                    slotDialog=false
                }){
                    Text("Save")
                }
            },dismissButton={
                TextButton(onClick={
                    slotDialog=false
                }){
                    Text("Cancel")
                }
            })
            if(addGoalDialog)AlertDialog(onDismissRequest={
                addGoalDialog=false
            },title={
                Text("Add learning goal")
            },text={
                EditBox("Goal title",newGoal){
                    newGoal=it
                }
            },confirmButton={
                TextButton(onClick={
                    addGoalDialog=false
                }){
                    Text("Add")
                }
            },dismissButton={
                TextButton(onClick={
                    addGoalDialog=false
                }){
                    Text("Cancel")
                }
            })
        }
    }
}
