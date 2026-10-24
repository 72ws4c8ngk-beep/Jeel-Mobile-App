package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.PrivacyScreen(state:JeelUiState) {
    with(state){
        PageHead("SETTINGS","Privacy",::back)
        Info("Your experience and your data stay yours.","This UI prototype uses local sample data. No information is sent to a server.")
        Panel{
            ToggleRow("Show profile to your mentor",shareProfile){
                shareProfile=it
            }
        }
        Action("Save preferences"){
            back()
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun PrivacyScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="privacy"){}
}
