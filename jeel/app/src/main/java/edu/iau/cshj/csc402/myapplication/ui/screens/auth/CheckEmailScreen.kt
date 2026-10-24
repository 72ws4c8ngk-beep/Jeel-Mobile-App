package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.CheckEmailScreen(state:AuthUiState) {
    with(state){
        Header("ACCOUNT RECOVERY", "Check your email", ::back)
        Spacer(Modifier.height(30.dp))
        Brand()
        Spacer(Modifier.height(24.dp))
        Title("Check your inbox.")
        Body("This is a design preview. No reset email has been sent.")
        Spacer(Modifier.height(24.dp))
        
        Spacer(Modifier.height(12.dp))
        Action("Back to login", secondary = true) {
            go("login")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun CheckEmailScreenPreview() {
    PreviewScreen { CheckEmailScreen(AuthUiState()) }
}
