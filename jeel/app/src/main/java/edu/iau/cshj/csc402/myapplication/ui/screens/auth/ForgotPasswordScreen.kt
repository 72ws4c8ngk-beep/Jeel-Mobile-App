
package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.JeelRepository
import edu.iau.cshj.csc402.myapplication.state.AuthUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun ColumnScope.ForgotPasswordScreen(
    state: AuthUiState,
    repository: JeelRepository,
    onContinue: () -> Unit
) {
    var checkEmail by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var secondsLeft by rememberSaveable { mutableIntStateOf(30) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(checkEmail, secondsLeft) {
        if (checkEmail && secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
    }

    Header(
        "ACCOUNT RECOVERY",
        if (checkEmail) "Check your email" else "Forgot password",
        state::back
    )

    Spacer(Modifier.height(30.dp))
    Brand()
    Spacer(Modifier.height(24.dp))

    if (!checkEmail) {

        Title("Let's get you back in.")
        Body("Enter your registered email to continue.")

        Spacer(Modifier.height(24.dp))

        Field(
            "Email",
            state.email,
            {
                state.email = it
                errorMessage = ""
            },
            emailField = true
        )

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(16.dp))

        Action(
            label = if (isLoading) "Checking..." else "Send reset link",
            enabled = !isLoading
        ) {
            val cleanEmail = state.email.trim().lowercase()

            when {
                cleanEmail.isBlank() ->
                    errorMessage = "Please enter your email."

                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(cleanEmail).matches() ->
                    errorMessage = "Please enter a valid email address."

                else -> {
                    isLoading = true
                    errorMessage = ""

                    scope.launch {
                        try {
                            val user = repository.getUserByEmail(cleanEmail)

                            if (user != null) {
                                state.email = cleanEmail
                                checkEmail = true
                                secondsLeft = 30
                            } else {
                                errorMessage =
                                    "No account found with this email."
                            }
                        } catch (e: Exception) {
                            errorMessage =
                                "Something went wrong. Please try again."
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }
        }

    } else {

        Title("Check your inbox.")
        Body("Your account was found. Continue to set a new password.")

        Spacer(Modifier.height(20.dp))

        Body(state.email)

        Spacer(Modifier.height(16.dp))

        Info(
            "Next step",
            "In this local prototype, continue to the password reset screen. No email has been sent."
        )

        Spacer(Modifier.height(16.dp))

        Action("Continue to reset password") {
            onContinue()
        }

        Spacer(Modifier.height(12.dp))

        Action(
            label = if (secondsLeft == 0)
                "Resend reset request"
            else
                "Resend · 00:${secondsLeft.toString().padStart(2, '0')}",
            enabled = secondsLeft == 0
        ) {
            secondsLeft = 30
        }

        Caption(
            if (secondsLeft == 0)
                "You can request again."
            else
                "You can request again in $secondsLeft seconds."
        )
    }

    Spacer(Modifier.height(16.dp))

    Action("Back to login", secondary = true) {
        state.go("login")
    }

    Footer("Your account information is stored locally on this device.")
}
