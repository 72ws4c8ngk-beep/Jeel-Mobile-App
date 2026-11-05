package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.theme.*

/** Static presentation inputs for Android Studio previews; no service calls or persistence. */
data class UiScene(val screen:Int=0,val title:String="",val state:String="Default",val role:String="Mentee")
internal val LocalUiScene=staticCompositionLocalOf { UiScene() }

@Composable
internal fun inputDisplayValue(label:String,value:String):String {
    val scene=LocalUiScene.current
    return when {
        (scene.state=="Required fields"||scene.state=="Incomplete data")&&label in listOf("Full name","Email","Password","Confirm password","Age","Study stage","Expertise") -> ""
        scene.state=="Invalid email"&&label=="Email" -> "aisha@email"
        scene.state=="Invalid age"&&label=="Age" -> "-2"
        scene.state=="Weak password"&&label=="Password" -> "12345"
        scene.state=="Passwords do not match"&&label=="Confirm password" -> "different"
        scene.state=="Unsaved changes"&&label=="Full name" -> if(scene.role=="Mentor") "Dr. Omar Al Haddad" else "Aisha Al Rahman"
        scene.state=="Password visible"&&label=="Password" -> "Jeel2026!"
        scene.screen!=0&&label in listOf("Password","Confirm password") -> "Jeel2026!"
        else -> value
    }
}

@Composable
internal fun inputError(label:String):String? {
    val s=LocalUiScene.current.state
    return when {
        s=="Required fields"||s=="Incomplete data" -> if(label in listOf("Full name","Email","Password","Confirm password","Age","Study stage","Expertise")) "This field is required." else null
        s=="Invalid email"&&label=="Email" -> "Enter a valid email address."
        s=="Email already used"&&label=="Email" -> "This email is already registered. Log in or use another email."
        s=="Weak password"&&label=="Password" -> "Use at least 8 characters, a number and a symbol."
        s=="Passwords do not match"&&label=="Confirm password" -> "Passwords do not match."
        s=="Invalid age"&&label=="Age" -> "Enter a valid age in whole years."
        else -> null
    }
}

@Composable
internal fun ColumnScope.UiStatePresentation(content:@Composable ColumnScope.()->Unit) {
    val scene=LocalUiScene.current
    val s=scene.state
    val empty=when(s) {
        "New account" -> if(scene.role=="Mentor") "Ready for your first mentee?" to "No requests or sessions yet. Add availability to get started." else "Welcome to your next chapter" to "No sessions or progress yet. Find a mentor to start your journey."
        "No results" -> "No mentors found" to "Try another name or skill, or clear your filters."
        "No sessions" -> "No sessions yet" to "Your conversations will appear here after booking."
        "No notifications" -> "No notifications yet" to "Booking updates, request decisions and session reminders will appear here."
        "No requests" -> "No requests yet" to "Keep your availability up to date. New requests will appear here."
        "No progress yet" -> "Your progress starts here" to "Complete your first session and set a mentorship goal to see your growth."
        "No appointments" -> "No appointments available" to "This mentor has no slots. Check back later or explore other mentors."
        "No availability" -> if(scene.screen==24) "No availability yet" to "Add a time slot so mentees can book a session." else null
        else -> null
    }
    when {
        s=="Loading"&&scene.screen !in listOf(1,3,4,6) -> {
            PageHead("JEEL",scene.title)
            repeat(3) { SkeletonCard() }
        }
        s.startsWith("Load failed") -> {
            PageHead("JEEL",scene.title)
            JeelErrorBanner("Unable to load data","Check your connection and try again.") { }
        }
        empty!=null -> {
            PageHead("JEEL",scene.title)
            Panel {
                Heading(empty.first,23)
                BodyText(empty.second)
                Action(if(scene.screen==24||scene.role=="Mentor"&&scene.screen==21) "Add time slot" else "Explore mentors") { }
            }
        }
        s=="Review sent" -> {
            PageHead("SESSION REVIEW","Thank you for your review")
            Info("Review submitted","Your rating and comment were sent successfully.")
            Action("Back to my sessions") { }
        }
        else -> {
            val error=when(s) {
                "Wrong credentials" -> "Incorrect email or password" to "Check your details or reset your password."
                "Connection failed" -> "Connection failed" to "Check your connection and retry. Your details are still here."
                "Registration failed" -> "Account could not be created" to "Your details are still here. Please try again."
                "Save failed","Update failed" -> "Changes could not be saved" to "Your edits are still here. Check your connection and retry."
                "Send failed" -> "Could not send" to "Your details are still here. Check your connection and retry."
                "Operation failed" -> "Booking could not be completed" to "Your selected time is preserved. Please retry."
                "Action failed" -> "Action could not be completed" to "No changes were made. Please retry."
                "Slot unavailable" -> "This time was just booked" to "Choose another time. Your booking has not been submitted."
                "Rating required" -> "Choose a rating first" to "Select at least one star before submitting your review."
                else -> null
            }
            if(error!=null) JeelErrorBanner(error.first,error.second) { }
            when(s) {
                "Profile saved" -> Info("Profile saved","Your updated information is now visible on your profile.")
                "Availability saved" -> Info("Availability saved","Your updated slots are available for new bookings.")
                "Unsaved changes" -> Info("Unsaved changes","Your edits have not been saved.")
                "Incomplete profile" -> Info("Complete your profile","Add your age, study details and mentorship goals from Edit Profile.")
                "Search results" -> Info("3 mentors found","Results for Product strategy.")
                "Reset" -> Info("Filters reset","All specialties, skills and available times are included.")
                "Apply" -> Info("Filters applied","3 mentors match your preferences.")
                "Goal complete" -> Info("Goal complete","You completed your mentorship goal. Set a new goal to keep growing.")
            }
            content()
        }
    }
}

@Composable
private fun SkeletonCard() {
    Panel {
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)).background(Mist))
            Column(verticalArrangement=Arrangement.spacedBy(12.dp)) {
                Box(Modifier.fillMaxWidth(.7f).height(14.dp).clip(RoundedCornerShape(7.dp)).background(Mist))
                Box(Modifier.fillMaxWidth(.5f).height(12.dp).clip(RoundedCornerShape(6.dp)).background(Mist))
            }
        }
        Box(Modifier.fillMaxWidth().height(54.dp).clip(RoundedCornerShape(12.dp)).background(Mist))
    }
}
