package edu.iau.cshj.csc402.myapplication.ui.screens.shared

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.MentorReviewsScreen(state:JeelUiState) {
    with(state){
        PageHead("MENTOR REVIEWS","What mentees say",::back)
        Stats(listOf(selectedMentor.rating to "Average rating","64" to "Reviews"))
        repeat(3){
            ReviewCard()
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun MentorReviewsScreenPreview() {
    JeelWorkspace("Mentee","","",initialRoute="reviews"){}
}
