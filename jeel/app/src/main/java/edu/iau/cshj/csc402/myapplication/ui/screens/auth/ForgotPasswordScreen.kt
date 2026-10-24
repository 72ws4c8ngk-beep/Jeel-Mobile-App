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
internal fun ColumnScope.ForgotPasswordScreen(state:AuthUiState) {
    with(state){
        Header("ACCOUNT RECOVERY", "Forgot password", ::back)
        Spacer(Modifier.height(30.dp))
        Brand()
        Spacer(Modifier.height(24.dp))
        Title("Let's get you back in.")
        Body("Enter your email and we'll help you reset your password.")
        Spacer(Modifier.height(24.dp))
        
            Field("Email", email, {
                email = it
            }, emailField = true)
            Action("Send reset link") {
                go("sent")
            }
        
        Spacer(Modifier.height(12.dp))
        Action("Back to login", secondary = true) {
            go("login")
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun ForgotPasswordScreenPreview() {
    PreviewScreen { ForgotPasswordScreen(AuthUiState()) }
}
