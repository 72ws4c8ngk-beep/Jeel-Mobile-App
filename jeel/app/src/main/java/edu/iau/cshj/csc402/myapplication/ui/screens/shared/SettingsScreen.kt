package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.SettingsScreen(state:JeelUiState) {
    with(state){
        PageHead("JEEL","Settings",::back)
        BodyText("A few preferences to make Jeel feel right for you.")
        Panel{
            SettingsRow("Language","English · العربية"){
                go("language")
            }
            HorizontalDivider(color=Edge)
            SettingsRow("Notifications","Session reminders and request updates"){
                go("notificationSettings")
            }
            HorizontalDivider(color=Edge)
            SettingsRow("Privacy","Manage your data and privacy preferences"){
                go("privacy")
            }
        }
        DangerButton("Logout"){
            confirmation="logout"
        }
        Caption("Jeel 1.0 · Guidance for every generation")
        Action("Preview ${if(isMentor)"Mentee" else "Mentor"} screens",secondary=true){
            role=if(isMentor)"Mentee" else "Mentor"
            photo=if(role=="Mentor")R.drawable.jeel_omar else R.drawable.jeel_aisha
            name=if(role=="Mentor")"Dr. Omar Haddad" else "Aisha Rahman"
            email=if(role=="Mentor")"omar.haddad@gmail.com" else "aisha.rahman@gmail.com"
            tab("home")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun SettingsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="settings"){}
}
