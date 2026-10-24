package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable
internal fun BookingErrorScreen(retry:()->Unit={
}){
    JeelTheme{
        JeelErrorBanner("Booking failed","Try again or choose a different time.",retry)
    }
}
@Preview(showBackground=true) @Composable private fun BookingErrorScreenPreview(){
    BookingErrorScreen()
}
