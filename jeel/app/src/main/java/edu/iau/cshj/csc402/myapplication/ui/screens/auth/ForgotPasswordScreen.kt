package edu.iau.cshj.csc402.myapplication.ui.screens.auth
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

import androidx.compose.runtime.saveable.rememberSaveable
@Composable
internal fun ColumnScope.ForgotPasswordScreen(state:AuthUiState) {
    var checkEmail by rememberSaveable { mutableStateOf(false) }
    val preview=LocalUiScene.current.state
    val sent=checkEmail||preview.startsWith("Check Email")||preview=="Resend enabled"
    Header("ACCOUNT RECOVERY",if(sent) "Check your email" else "Forgot password",state::back)
    Spacer(Modifier.height(30.dp))
    Brand()
    Spacer(Modifier.height(24.dp))
    Title(if(sent) "Check your inbox." else "Let's get you back in.")
    Body(if(sent) "If an account exists for this email, we'll send a password reset link to:" else "Enter your email and we'll help you reset your password.")
    Spacer(Modifier.height(24.dp))
    if(sent) {
        Body(state.email.ifBlank { "aisha.rahman@email.com" })
        Info("Your next step","Open the email and follow the reset link to choose a new password.")
        Body("Can't find it? Check your spam folder, or wait before requesting another link.")
        val canResend=preview=="Resend enabled"
        Action(if(canResend) "Resend reset link" else "Resend link · 00:30",enabled=canResend) { }
        Caption(if(canResend) "You can request another link." else "You can resend in 30 seconds.")
    } else {
        Field("Email",state.email,{state.email=it},emailField=true)
        Action("Send reset link") { checkEmail=true }
    }
    Spacer(Modifier.height(12.dp))
    Action("Back to login",secondary=true) { state.go("login") }
    Footer("For your privacy, we won't confirm whether an email is registered.")
}
