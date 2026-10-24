package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*

import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.NotificationSettingsScreen(state:JeelUiState) {
    with(state){
        PageHead("SETTINGS","Notification preferences",::back)
        Panel{
            ToggleRow("Session reminders",reminders){
                reminders=it
            }
            ToggleRow("Request updates",updates){
                updates=it
            }
        }
        Caption("These preferences apply to this preview only.")
        Action("Save preferences"){
            back()
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun NotificationSettingsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="notificationSettings"){}
}
