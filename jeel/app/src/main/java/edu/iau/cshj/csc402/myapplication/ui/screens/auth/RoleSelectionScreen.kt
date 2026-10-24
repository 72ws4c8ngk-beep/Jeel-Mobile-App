package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.RoleSelectionScreen(state:AuthUiState) {
    with(state){
        Header("STEP 1 OF 2", "Choose your path", ::back)
        Title("How will you join Jeel?")
        Spacer(Modifier.height(16.dp))
        Body("Every meaningful journey starts with the right role.")
        Spacer(Modifier.height(24.dp))
        RoleCard("Mentee", "Find guidance from someone who has been there. Explore your interests and take your next career step.", role == "Mentee") {
            role = "Mentee"
        }
        Spacer(Modifier.height(20.dp))
        RoleCard("Mentor", "Share your professional experience. Guide the next generation with practical advice that makes a difference.", role == "Mentor") {
            role = "Mentor"
        }
        Spacer(Modifier.height(24.dp))
        Action("→  Continue as $role") {
            go("profile")
        }
        Footer("Your role shapes your mentorship experience.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun RoleSelectionScreenPreview() {
    PreviewScreen { RoleSelectionScreen(AuthUiState()) }
}
