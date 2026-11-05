package edu.iau.cshj.csc402.myapplication.ui.preview

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import edu.iau.cshj.csc402.myapplication.navigation.*
import edu.iau.cshj.csc402.myapplication.state.AuthUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.screens.auth.*
import edu.iau.cshj.csc402.myapplication.ui.theme.JeelTheme

/** 26 core screens plus 124 visual state examples. No backend behavior is attached. */
class JeelSceneProvider : PreviewParameterProvider<UiScene> {
    override val values=sequenceOf(
        UiScene(1,"Splash Screen","Default","Mentee"),
        UiScene(2,"Welcome","Default","Mentee"),
        UiScene(3,"Login","Default","Mentee"),
        UiScene(4,"Sign Up","Default","Mentee"),
        UiScene(5,"Profile Onboarding","Default","Mentee"),
        UiScene(6,"Forgot Password","Default","Mentee"),
        UiScene(7,"Mentee Home","Default","Mentee"),
        UiScene(8,"Explore Mentors","Default","Mentee"),
        UiScene(9,"Mentor Filters","Default","Mentee"),
        UiScene(10,"Mentor Details","Default","Mentee"),
        UiScene(11,"Book Session · Reschedule","Default","Mentee"),
        UiScene(12,"Booking Confirmation","Default","Mentee"),
        UiScene(13,"My Sessions","Default","Mentee"),
        UiScene(14,"Session Details","Default","Mentee"),
        UiScene(15,"Session Review","Default","Mentee"),
        UiScene(16,"Notifications","Default","Mentee"),
        UiScene(17,"My Profile","Default","Mentee"),
        UiScene(18,"Edit Mentee Profile","Default","Mentee"),
        UiScene(19,"Edit Mentor Profile","Default","Mentor"),
        UiScene(20,"My Progress","Default","Mentee"),
        UiScene(21,"Mentor Home","Default","Mentor"),
        UiScene(22,"Mentorship Requests","Default","Mentor"),
        UiScene(23,"Request Details","Default","Mentor"),
        UiScene(24,"Manage Availability","Default","Mentor"),
        UiScene(25,"Mentor Sessions","Default","Mentor"),
        UiScene(26,"Settings","Default","Mentee"),
        UiScene(3,"Login","Required fields","Mentee"),
        UiScene(3,"Login","Invalid email","Mentee"),
        UiScene(3,"Login","Wrong credentials","Mentee"),
        UiScene(3,"Login","Password visible","Mentee"),
        UiScene(3,"Login","Loading","Mentee"),
        UiScene(3,"Login","Connection failed","Mentee"),
        UiScene(4,"Sign Up","Mentor selected","Mentor"),
        UiScene(4,"Sign Up","Required fields","Mentee"),
        UiScene(4,"Sign Up","Invalid email","Mentee"),
        UiScene(4,"Sign Up","Email already used","Mentee"),
        UiScene(4,"Sign Up","Weak password","Mentee"),
        UiScene(4,"Sign Up","Passwords do not match","Mentee"),
        UiScene(4,"Sign Up","Loading","Mentee"),
        UiScene(4,"Sign Up","Registration failed","Mentee"),
        UiScene(5,"Profile Onboarding","Incomplete data","Mentee"),
        UiScene(5,"Profile Onboarding","Invalid age","Mentee"),
        UiScene(5,"Profile Onboarding","Saving","Mentee"),
        UiScene(5,"Profile Onboarding","Save failed","Mentee"),
        UiScene(6,"Forgot Password","Invalid email","Mentee"),
        UiScene(6,"Forgot Password","Loading","Mentee"),
        UiScene(6,"Forgot Password","Resend enabled","Mentee"),
        UiScene(6,"Forgot Password","Send failed","Mentee"),
        UiScene(7,"Mentee Home","New account","Mentee"),
        UiScene(7,"Mentee Home","Loading","Mentee"),
        UiScene(7,"Mentee Home","Load failed · Retry","Mentee"),
        UiScene(8,"Explore Mentors","Search results","Mentee"),
        UiScene(8,"Explore Mentors","No results","Mentee"),
        UiScene(8,"Explore Mentors","Loading","Mentee"),
        UiScene(8,"Explore Mentors","Load failed · Retry","Mentee"),
        UiScene(9,"Mentor Filters","No filters","Mentee"),
        UiScene(9,"Mentor Filters","Reset","Mentee"),
        UiScene(9,"Mentor Filters","Apply","Mentee"),
        UiScene(10,"Mentor Details","No availability","Mentee"),
        UiScene(10,"Mentor Details","No reviews","Mentee"),
        UiScene(10,"Mentor Details","Loading","Mentee"),
        UiScene(10,"Mentor Details","Load failed · Retry","Mentee"),
        UiScene(11,"Book Session · Reschedule","No slot selected","Mentee"),
        UiScene(11,"Book Session · Reschedule","No appointments","Mentee"),
        UiScene(11,"Book Session · Reschedule","Slot unavailable","Mentee"),
        UiScene(11,"Book Session · Reschedule","Processing","Mentee"),
        UiScene(11,"Book Session · Reschedule","Operation failed","Mentee"),
        UiScene(12,"Booking Confirmation","Reschedule confirmed","Mentee"),
        UiScene(12,"Booking Confirmation","Awaiting mentor approval","Mentee"),
        UiScene(13,"My Sessions","Completed tab","Mentee"),
        UiScene(13,"My Sessions","Cancelled tab","Mentee"),
        UiScene(13,"My Sessions","No sessions","Mentee"),
        UiScene(13,"My Sessions","Loading","Mentee"),
        UiScene(13,"My Sessions","Load failed · Retry","Mentee"),
        UiScene(14,"Session Details","Pending approval","Mentee"),
        UiScene(14,"Session Details","Zoom link available","Mentee"),
        UiScene(14,"Session Details","Completed","Mentee"),
        UiScene(14,"Session Details","Cancelled","Mentee"),
        UiScene(14,"Session Details","Confirm cancellation","Mentee"),
        UiScene(14,"Session Details","Action failed","Mentee"),
        UiScene(15,"Session Review","Rating selected","Mentee"),
        UiScene(15,"Session Review","Rating required","Mentee"),
        UiScene(15,"Session Review","Sending","Mentee"),
        UiScene(15,"Session Review","Review sent","Mentee"),
        UiScene(15,"Session Review","Send failed","Mentee"),
        UiScene(16,"Notifications","Read","Mentee"),
        UiScene(16,"Notifications","No notifications","Mentee"),
        UiScene(16,"Notifications","Loading","Mentee"),
        UiScene(16,"Notifications","Load failed · Retry","Mentee"),
        UiScene(17,"My Profile","Incomplete profile","Mentee"),
        UiScene(17,"My Profile","Loading","Mentee"),
        UiScene(17,"My Profile","Load failed · Retry","Mentee"),
        UiScene(18,"Edit Mentee Profile","Unsaved changes","Mentee"),
        UiScene(18,"Edit Mentee Profile","Invalid age","Mentee"),
        UiScene(18,"Edit Mentee Profile","Saving","Mentee"),
        UiScene(18,"Edit Mentee Profile","Profile saved","Mentee"),
        UiScene(18,"Edit Mentee Profile","Save failed","Mentee"),
        UiScene(18,"Edit Mentee Profile","Discard changes","Mentee"),
        UiScene(19,"Edit Mentor Profile","Unsaved changes","Mentor"),
        UiScene(19,"Edit Mentor Profile","Invalid age","Mentor"),
        UiScene(19,"Edit Mentor Profile","Saving","Mentor"),
        UiScene(19,"Edit Mentor Profile","Profile saved","Mentor"),
        UiScene(19,"Edit Mentor Profile","Save failed","Mentor"),
        UiScene(19,"Edit Mentor Profile","Discard changes","Mentor"),
        UiScene(20,"My Progress","No progress yet","Mentee"),
        UiScene(20,"My Progress","Goal complete","Mentee"),
        UiScene(20,"My Progress","Loading","Mentee"),
        UiScene(20,"My Progress","Load failed · Retry","Mentee"),
        UiScene(21,"Mentor Home","New account","Mentor"),
        UiScene(21,"Mentor Home","Loading","Mentor"),
        UiScene(21,"Mentor Home","Load failed · Retry","Mentor"),
        UiScene(22,"Mentorship Requests","Accepted tab","Mentor"),
        UiScene(22,"Mentorship Requests","Rejected tab","Mentor"),
        UiScene(22,"Mentorship Requests","No requests","Mentor"),
        UiScene(22,"Mentorship Requests","Loading","Mentor"),
        UiScene(22,"Mentorship Requests","Load failed · Retry","Mentor"),
        UiScene(23,"Request Details","Accepted","Mentor"),
        UiScene(23,"Request Details","Rejected","Mentor"),
        UiScene(23,"Request Details","Confirm acceptance","Mentor"),
        UiScene(23,"Request Details","Confirm rejection","Mentor"),
        UiScene(23,"Request Details","Processing","Mentor"),
        UiScene(23,"Request Details","Action failed","Mentor"),
        UiScene(24,"Manage Availability","No availability","Mentor"),
        UiScene(24,"Manage Availability","Add slot","Mentor"),
        UiScene(24,"Manage Availability","Edit slot","Mentor"),
        UiScene(24,"Manage Availability","Invalid time","Mentor"),
        UiScene(24,"Manage Availability","Overlapping slots","Mentor"),
        UiScene(24,"Manage Availability","Confirm deletion","Mentor"),
        UiScene(24,"Manage Availability","Saving","Mentor"),
        UiScene(24,"Manage Availability","Availability saved","Mentor"),
        UiScene(24,"Manage Availability","Save failed","Mentor"),
        UiScene(25,"Mentor Sessions","Completed tab","Mentor"),
        UiScene(25,"Mentor Sessions","Cancelled tab","Mentor"),
        UiScene(25,"Mentor Sessions","No sessions","Mentor"),
        UiScene(25,"Mentor Sessions","Loading","Mentor"),
        UiScene(25,"Mentor Sessions","Load failed · Retry","Mentor"),
        UiScene(26,"Settings","Arabic selected","Mentee"),
        UiScene(26,"Settings","Notifications off","Mentee"),
        UiScene(26,"Settings","Saving","Mentee"),
        UiScene(26,"Settings","Update failed","Mentee"),
        UiScene(26,"Settings","Confirm logout","Mentee"),
        UiScene(5,"Profile Onboarding","Mentor","Mentor"),
        UiScene(6,"Forgot Password","Check Email · Countdown","Mentee"),
        UiScene(11,"Book Session · Reschedule","Reschedule","Mentee"),
        UiScene(14,"Session Details","Mentor · Upcoming","Mentor"),
        UiScene(17,"My Profile","Mentor","Mentor"),
        UiScene(5,"Profile Onboarding","Incomplete data","Mentor"),
        UiScene(5,"Profile Onboarding","Invalid age","Mentor"),
        UiScene(5,"Profile Onboarding","Saving","Mentor"),
        UiScene(5,"Profile Onboarding","Save failed","Mentor")
    )
    override fun getDisplayName(index:Int):String?=values.elementAt(index).let { it.screen.toString().padStart(2,'0')+" · "+it.title+" · "+it.role+" · "+it.state }
}

@Preview(name="JEEL · Screens and states",group="JEEL 26",showBackground=true,widthDp=412,heightDp=892)
@Composable
private fun JeelScreenPreview(@PreviewParameter(JeelSceneProvider::class) scene:UiScene) {
    CompositionLocalProvider(LocalUiScene provides scene) {
        when(scene.screen) {
            1 -> JeelTheme { SplashScreen() }
            5 -> JeelOnboarding(scene.role,"Aisha Rahman",{},{})
            2,3,4,6 -> {
                val state=remember(scene) { AuthUiState().apply {
                    role=scene.role
                    fullName="Aisha Rahman"
                    email="aisha.rahman@email.com"
                } }
                PreviewScreen {
                    UiStatePresentation {
                        when(scene.screen) {
                            2 -> WelcomeScreen(state)
                            3 -> LoginScreen(state)
                            4 -> SignUpScreen(state)
                            6 -> ForgotPasswordScreen(state)
                        }
                    }
                }
            }
            else -> JeelWorkspace(scene.role,"","",initialRoute=when(scene.screen) {
                7 -> "home"
                8 -> "explore"
                9 -> "filters"
                10 -> "mentor"
                11 -> "book"
                12 -> "confirmation"
                13 -> "sessions"
                14 -> "session"
                15 -> "review"
                16 -> "notifications"
                17 -> "profile"
                18 -> "editProfile"
                19 -> "editProfile"
                20 -> "progress"
                21 -> "home"
                22 -> "requests"
                23 -> "request"
                24 -> "availability"
                25 -> "sessions"
                26 -> "settings"
                else -> "home"
            }) { }
        }
    }
}
