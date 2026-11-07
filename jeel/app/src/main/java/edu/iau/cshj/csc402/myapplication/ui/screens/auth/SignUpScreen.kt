package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.data.JeelRepository
import edu.iau.cshj.csc402.myapplication.data.local.UserEntity
import edu.iau.cshj.csc402.myapplication.data.security.PasswordHasher
import edu.iau.cshj.csc402.myapplication.state.AuthUiState
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import kotlinx.coroutines.launch

@Composable
internal fun ColumnScope.SignUpScreen(
    state: AuthUiState,
    repository: JeelRepository? = null,
    onSuccess: () -> Unit = {}
) {
    with(state) {

        val scope = rememberCoroutineScope()

        var password by rememberSaveable { mutableStateOf("") }
        var confirmation by rememberSaveable { mutableStateOf("") }
        var agreed by rememberSaveable { mutableStateOf(false) }
        var isSaving by rememberSaveable { mutableStateOf(false) }
        var errorMessage by rememberSaveable { mutableStateOf("") }

        Header("STEP 1 OF 2 · CHOOSE YOUR ROLE", "Create your account", ::back)

        Title("Start with a connection.")
        Body("A few details to make your mentorship journey personal.")

        Spacer(Modifier.height(20.dp))

        RoleCard(
            "Mentee",
            "Find guidance from someone who has been there. Explore your interests and take your next career step.",
            role == "Mentee"
        ) {
            role = "Mentee"
        }

        Spacer(Modifier.height(20.dp))

        RoleCard(
            "Mentor",
            "Share your professional experience. Guide the next generation with practical advice that makes a difference.",
            role == "Mentor"
        ) {
            role = "Mentor"
        }

        Spacer(Modifier.height(22.dp))

        Field("Full name", fullName, {
            fullName = it
        })

        Field("Email", email, {
            email = it
        }, emailField = true)

        Field("Password", password, {
            password = it
        }, secret = true)

        Body("Use at least 8 characters, a number and a symbol.")

        Spacer(Modifier.height(16.dp))

        Field("Confirm password", confirmation, {
            confirmation = it
        }, secret = true)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agreed,
                onCheckedChange = {
                    agreed = it
                }
            )

            Text(
                "I agree to the Terms of service and Privacy policy.",
                fontSize = 12.sp,
                color = Slate
            )
        }

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Action(
            label = if (isSaving) "Creating account..." else "Create account as $role",
            enabled = !isSaving
        ) {

            val cleanName = fullName.trim()
            val cleanEmail = email.trim().lowercase()

            errorMessage = when {
                cleanName.length < 2 ->
                    "Please enter your full name."

                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(cleanEmail).matches() ->
                    "Please enter a valid email address."

                password.length < 8 ->
                    "Password must contain at least 8 characters."

                password.none { it.isDigit() } ->
                    "Password must contain at least one number."

                password.none { !it.isLetterOrDigit() } ->
                    "Password must contain at least one symbol."

                password != confirmation ->
                    "Passwords do not match."

                !agreed ->
                    "Please accept the Terms of service and Privacy policy."

                else -> ""
            }

            if (errorMessage.isBlank()) {

                isSaving = true

                scope.launch {
                    try {

                        // Check that the repository is available
                        val repo = repository

                        if (repo == null) {
                            errorMessage = "Registration is not available. Please try again."
                            return@launch
                        }

                        // Check whether the email is already registered
                        val existingUser = repo.getUserByEmail(cleanEmail)

                        if (existingUser != null) {

                            errorMessage =
                                "This email is already registered. Please log in."

                        } else {

                            // Generate password salt and hash
                            val salt = PasswordHasher.generateSalt()

                            val hash = PasswordHasher.hashPassword(
                                password,
                                salt
                            )

                            // Save the new user in Room Database
                            repo.insertUser(
                                UserEntity(
                                    fullName = cleanName,
                                    email = cleanEmail,
                                    role = role,
                                    passwordHash = hash,
                                    passwordSalt = salt
                                )
                            )

                            fullName = cleanName
                            email = cleanEmail
                            notice = "Account created successfully."

                            onSuccess()
                        }

                    } catch (e: Exception) {

                        errorMessage =
                            "Unable to create account. Please try again."

                    } finally {
                        isSaving = false
                    }
                }
            }
        }

        TextButton(
            onClick = {
                go("login")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Already have an account? Login", color = Gold)
        }
    }
}