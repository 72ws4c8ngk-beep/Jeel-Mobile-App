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
    val state=remember{
        JeelUiState(initialRole,initialName,initialEmail,initialRoute,initialProfile)
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
                    Column(Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState()).padding(horizontal=20.dp,vertical=16.dp),verticalArrangement=Arrangement.spacedBy(18.dp)){
                        when(route){
                            "home" -> if(isMentor) MentorHomeScreen(state) else MenteeHomeScreen(state)
                            "explore" -> ExploreScreen(state)
                            "filters" -> FilterScreen(state)
                            "mentor" -> MentorDetailScreen(state)
                            "reviews" -> MentorReviewsScreen(state)
                            "match" -> FindMatchScreen(state)
                            "book","reschedule" -> if(rescheduling) RescheduleScreen(state) else BookingScreen(state)
                            "confirmation" -> BookingConfirmationScreen(state)
                            "sessions" -> if(isMentor) MentorSessionsScreen(state) else MenteeSessionsScreen(state)
                            "session" -> if(isMentor) MentorSessionDetailScreen(state) else MenteeSessionDetailScreen(state)
                            "review" -> SessionReviewScreen(state)
                            "requests" -> RequestsScreen(state)
                            "request" -> RequestDetailScreen(state)
                            "availability" -> AvailabilityScreen(state)
                            "progress" -> ProgressScreen(state)
                            "profile" -> if(isMentor) MentorProfileScreen(state) else MenteeProfileScreen(state)
                            "editProfile" -> if(isMentor) MentorEditProfileScreen(state) else MenteeEditProfileScreen(state)
                            "notifications" -> if(isMentor) MentorNotificationsScreen(state) else MenteeNotificationsScreen(state)
                            "settings" -> SettingsScreen(state)
                            "language" -> LanguageScreen(state)
                            "notificationSettings" -> NotificationSettingsScreen(state)
                            "privacy" -> PrivacyScreen(state)
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
                    else->"Decline this request?"
                })
            },text={
                Text("Design preview only.")
            },confirmButton={
                TextButton(onClick={
                    when(confirmation){
                        "logout" -> logout()
                        "cancel" -> tab("sessions")
                        "decline" -> tab("requests")
                    }
                    confirmation=""
                }){
                    Text("Confirm")
                }
            },dismissButton={
                TextButton(onClick={
                    confirmation=""
                }){
                    Text("Keep")
                }
            })
            if(slotDialog)AlertDialog(onDismissRequest={
                slotDialog=false
            },title={
                Text(if(editSlot<0)"Add time slot" else "Edit time slot")
            },text={
                EditBox("Start time",slotText){
                    slotText=it
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
