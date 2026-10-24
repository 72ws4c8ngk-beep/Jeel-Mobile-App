package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun NetworkErrorScreen(retry:()->Unit={
}){
    JeelTheme{
        JeelErrorBanner("Network error","Check your connection and try again.",retry)
    }
}
@Preview(showBackground=true) @Composable private fun NetworkErrorScreenPreview(){
    NetworkErrorScreen()
}
