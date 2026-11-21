
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
import kotlinx.coroutines.launch

@Composable
internal fun ColumnScope.ResetPasswordScreen(
    state: AuthUiState,
    repository: JeelRepository
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Header("ACCOUNT RECOVERY", "Reset password", state::back)

    Spacer(Modifier.height(30.dp))
    Brand()
    Spacer(Modifier.height(24.dp))

    Title("Create a new password.")
    Body("Choose a strong password for your Jeel account.")

    Spacer(Modifier.height(24.dp))

    Field(
        "New password",
        password,
        {
            password = it
            errorMessage = ""
        },
        secret = true
    )

    Spacer(Modifier.height(12.dp))

    Field(
        "Confirm new password",
        confirmPassword,
        {
            confirmPassword = it
            errorMessage = ""
        },
        secret = true
    )

    Spacer(Modifier.height(8.dp))

    Caption("Password must contain at least 8 characters.")

    if (errorMessage.isNotBlank()) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
    }

    Spacer(Modifier.height(20.dp))

    Action(
        label = if (isLoading) "Updating..." else "Update password",
        enabled = !isLoading
    ) {
        when {
            password.isBlank() || confirmPassword.isBlank() ->
                errorMessage = "Please fill in both password fields."

            password.length < 8 ->
                errorMessage = "Password must be at least 8 characters."

            password != confirmPassword ->
                errorMessage = "Passwords do not match."

            else -> {
                isLoading = true
                errorMessage = ""

                scope.launch {
                    try {
                        val updated = repository.resetPassword(
                            email = state.email,
                            newPassword = password
                        )

                        if (updated) {
                            state.go("login")
                            state.notice = "Password updated successfully. Please log in."
                        } else {
                            errorMessage =
                                "Unable to update password. Please try again."
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

    Spacer(Modifier.height(12.dp))

    Action("Back to login", secondary = true) {
        state.go("login")
    }
}
