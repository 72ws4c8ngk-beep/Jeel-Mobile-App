
package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.data.JeelRepository
import edu.iau.cshj.csc402.myapplication.data.security.PasswordHasher
import edu.iau.cshj.csc402.myapplication.state.AuthUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import kotlinx.coroutines.launch

@Composable
internal fun ColumnScope.LoginScreen(
    state: AuthUiState,
    repository: JeelRepository? = null,
    onSuccess: () -> Unit = {}
) {
    with(state) {

        val scope = rememberCoroutineScope()

        var password by rememberSaveable {
            mutableStateOf("")
        }

        var isLoading by rememberSaveable {
            mutableStateOf(false)
        }

        var errorMessage by rememberSaveable {
            mutableStateOf("")
        }

        Header("WELCOME BACK", "Login", ::back)

        Spacer(Modifier.height(36.dp))

        Brand()

        Spacer(Modifier.height(28.dp))

        Title("Good to see you again.")
        Body("Your next conversation is waiting.")

        Spacer(Modifier.height(30.dp))

        Field("Email", email, {
            email = it
            errorMessage = ""
        }, emailField = true)

        Field("Password", password, {
            password = it
            errorMessage = ""
        }, secret = true)

        TextButton(
            onClick = {
                go("reset")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot password?", color = Gold)
        }

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        Action(
            label = if (isLoading) "Logging in..." else "Login",
            enabled = !isLoading
        ) {

            val cleanEmail = email.trim().lowercase()

            errorMessage = when {
                cleanEmail.isBlank() ->
                    "Please enter your email."

                password.isBlank() ->
                    "Please enter your password."

                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(cleanEmail).matches() ->
                    "Please enter a valid email address."

                else -> ""
            }

            if (errorMessage.isBlank()) {
                isLoading = true

                scope.launch {
                    try {
                        val repo = repository

                        if (repo == null) {
                            errorMessage =
                                "Login is not available. Please try again."
                            return@launch
                        }

                        val user = repo.getUserByEmail(cleanEmail)

                        if (user == null) {
                            errorMessage =
                                "No account found with this email."
                        } else if (user.passwordSalt.isBlank() ||
                            user.passwordHash.isBlank()
                        ) {
                            errorMessage =
                                "This account needs to be registered again."
                        } else {
                            val isPasswordCorrect =
                                PasswordHasher.verifyPassword(
                                    password,
                                    user.passwordSalt,
                                    user.passwordHash
                                )

                            if (isPasswordCorrect) {
                                fullName = user.fullName
                                email = user.email
                                role = user.role

                                // Restore saved profile data from Room
                                onboardingProfile.clear()

                                if (user.profileData.isNotBlank()) {
                                    val profileJson = org.json.JSONObject(user.profileData)

                                    profileJson.keys().forEach { key ->
                                        onboardingProfile[key] = profileJson.getString(key)
                                    }
                                }

                                onSuccess()
                            } else {
                                errorMessage =
                                    "Incorrect password. Please try again."
                            }
                        }

                    } catch (e: Exception) {
                        errorMessage =
                            "Unable to log in. Please try again."
                    } finally {
                        isLoading = false
                    }
                }
            }
        }

        TextButton(
            onClick = {
                go("signup")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("New to Jeel? Sign up", color = Gold)
        }

        Spacer(Modifier.height(48.dp))

        Body("Your experience and your data stay yours.")
    }
}
