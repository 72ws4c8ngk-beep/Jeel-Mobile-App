
package edu.iau.cshj.csc402.myapplication.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import edu.iau.cshj.csc402.myapplication.data.local.MentorEntity

import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

import edu.iau.cshj.csc402.myapplication.ui.screens.mentee.*
import edu.iau.cshj.csc402.myapplication.ui.screens.mentor.*
import edu.iau.cshj.csc402.myapplication.ui.screens.shared.*
import edu.iau.cshj.csc402.myapplication.ui.screens.auth.*

import edu.iau.cshj.csc402.myapplication.data.JeelRepository

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun JeelApp() {

    val state = rememberSaveable(saver = AuthUiState.Saver) {
        AuthUiState()
    }

    val context = LocalContext.current

    val repository = remember(context) {
        JeelRepository.getRepository(context)
    }

    val mentorProfiles by remember(repository) {
        repository.getMentorProfiles()
    }.collectAsState(initial = emptyList())

    // Coroutine scope for database operations
    val scope = rememberCoroutineScope()

    with(state) {

        LaunchedEffect(screen) {
            if (screen == "splash") {
                delay(1800)
                screen = "welcome"
            }
        }

        BackHandler(screen != "welcome" && screen != "splash") {
            back()
        }

        JeelTheme {

            when (screen) {

                "splash" -> SplashScreen()

                // Profile onboarding after Sign Up
                "profile" -> JeelOnboarding(
                    role = role,
                    fullName = fullName,
                    back = {
                        go("signup")
                    },
                    complete = { profile ->

                        scope.launch {
                            try {

                                val user = repository.getUserByEmail(
                                    email.trim().lowercase()
                                )

                                if (user != null) {

                                    val profileJson =
                                        JSONObject(profile).toString()

                                    repository.updateUser(
                                        user.copy(
                                            profileData = profileJson,
                                            bio = profile["description"] ?: user.bio,
                                            experience = profile["experience"] ?: user.experience
                                        )
                                    )
                                    if (user.role == "Mentor") {
                                        val specialization = profile["specialization"] ?: "Product strategy"

                                        val years = Regex("""\d+""")
                                            .find(profile["experience"].orEmpty())
                                            ?.value
                                            ?.toIntOrNull() ?: 0

                                        val existingMentor = repository.getMentorByUserId(user.userId)

                                        if (existingMentor == null) {
                                            repository.insertMentor(
                                                MentorEntity(
                                                    userId = user.userId,
                                                    specialization = specialization,
                                                    yearsOfExperience = years
                                                )
                                            )
                                        } else {
                                            repository.updateMentor(
                                                existingMentor.copy(
                                                    specialization = specialization,
                                                    yearsOfExperience = years
                                                )
                                            )
                                        }
                                    }
                                    onboardingProfile.clear()
                                    onboardingProfile.putAll(profile)

                                    go("workspace")

                                } else {

                                    notice =
                                        "User account not found. Please sign up again."

                                    go("signup")
                                }

                            } catch (e: Exception) {

                                notice =
                                    "Unable to save your profile. Please try again."
                            }
                        }
                    }
                )

                // Main workspace
                "workspace" -> JeelWorkspace(
                    initialRole = role,
                    initialName = fullName,
                    initialEmail = email,
                    initialProfile = onboardingProfile,
                    mentorProfiles = mentorProfiles,
                    onProfileSaved = { savedName, savedEmail, profile ->
                        fullName = savedName
                        email = savedEmail
                        onboardingProfile = HashMap(profile)
                    },
                    logout = {
                        go("welcome")
                    }
                )

                else -> Surface(
                    color = Cream,
                    modifier = Modifier.fillMaxSize()
                ) {

                    key(screen) {

                        Column(
                            Modifier
                                .fillMaxSize()
                                .safeDrawingPadding()
                                .imePadding()
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp)
                        ) {

                            UiStatePresentation {

                                when (screen) {

                                    "welcome" -> WelcomeScreen(state)

                                    "login" -> LoginScreen(
                                        state = state,
                                        repository = repository,
                                        onSuccess = {
                                            go("workspace")
                                        }
                                    )

                                    "signup" -> SignUpScreen(
                                        state = state,
                                        repository = repository,
                                        onSuccess = {
                                            go("profile")
                                        }
                                    )

                                    "reset" -> ForgotPasswordScreen(state)
                                }
                            }

                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
