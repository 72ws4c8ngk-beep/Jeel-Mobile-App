package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.LanguageScreen(state:JeelUiState) {
    with(state){
        PageHead("SETTINGS","Language",::back)
        BodyText("Choose your language preference.")
        Segments(listOf("English","العربية"),language){
            language=it
        }
        Info("Design preview","The supplied screen designs are in English. Your preference is saved locally; translated screens are not included in the reference.")
        Action("Save preference"){
            back()
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun LanguageScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="language"){}
}
