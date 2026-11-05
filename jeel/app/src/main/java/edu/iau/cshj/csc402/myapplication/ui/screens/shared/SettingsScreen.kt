package edu.iau.cshj.csc402.myapplication.ui.screens.shared
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun ColumnScope.SettingsScreen(state:JeelUiState) {
    with(state) {
        PageHead("JEEL","Settings",::back)
        BodyText("A few preferences to make Jeel feel right for you.")
        if(LocalUiScene.current.state=="Saving") {
            Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                CircularProgressIndicator(Modifier.size(18.dp),strokeWidth=2.dp,color=Navy)
                Caption("Saving preferences…")
            }
        }
        Heading("Language",18)
        Picker("App language",language,listOf("English","العربية")) { language=it }
        Heading("Notification preferences",18)
        Panel {
            ToggleRow("Session reminders",reminders) { reminders=it }
            Caption("Push and email reminders for your next Zoom session.")
            HorizontalDivider(color=Edge)
            ToggleRow("Request and booking updates",updates) { updates=it }
            Caption("Booking confirmations and accepted or rejected requests.")
        }
        Heading("Account settings",18)
        Panel {
            Detail("✉","Email",email)
            SettingsRow("Password","Update your account password") { }
            HorizontalDivider(color=Edge)
            SettingsRow("Privacy","Manage your data and privacy preferences") { }
        }
        DangerButton("Logout") { confirmation="logout" }
        Caption("Jeel 1.0 · Guidance for every generation")
    }
}
