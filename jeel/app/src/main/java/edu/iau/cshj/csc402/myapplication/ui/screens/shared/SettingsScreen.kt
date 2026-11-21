package edu.iau.cshj.csc402.myapplication.ui.screens.shared
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.state.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*

private val Context.notificationPreferences by preferencesDataStore("notification_preferences")
private val sessionRemindersKey = booleanPreferencesKey("session_reminders")
private val bookingUpdatesKey = booleanPreferencesKey("booking_updates")

@Composable
internal fun ColumnScope.SettingsScreen(
    state: JeelUiState,
    onChangePassword: suspend (String, String) -> Boolean
) {
    val context = LocalContext.current
    val preview = LocalInspectionMode.current
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(context, state, preview) {
        if (!preview) {
            context.notificationPreferences.data
                .catch { error ->
                    if (error !is IOException) throw error
                    errorMessage = "Could not load notification preferences."
                }
                .collect { preferences ->
                    state.reminders = preferences[sessionRemindersKey] ?: true
                    state.updates = preferences[bookingUpdatesKey] ?: true
                }
        }
    }

    fun savePreference(key: Preferences.Key<Boolean>, enabled: Boolean) {
        scope.launch {
            try {
                errorMessage = null
                context.notificationPreferences.edit { preferences ->
                    preferences[key] = enabled
                }
            } catch (_: IOException) {
                errorMessage = "Could not save notification preferences."
            }
        }
    }

    var showPasswordDialog by remember { mutableStateOf(false) }
    var passwordChanged by remember { mutableStateOf(false) }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onChangePassword = onChangePassword,
            onDismiss = { showPasswordDialog = false },
            onSuccess = {
                showPasswordDialog = false
                passwordChanged = true
            }
        )
    }

    with(state) {
        PageHead("JEEL","Settings",::back)
        BodyText("A few preferences to make Jeel feel right for you.")
        if(LocalUiScene.current.state=="Saving") {
            Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                CircularProgressIndicator(Modifier.size(18.dp),strokeWidth=2.dp,color=Navy)
                Caption("Saving preferences…")
            }
        }
        Heading("Language",18)
        Picker("App language",language,listOf("English","العربية")) { language=it }
        Heading("Notification preferences",18)
        Panel {
            ToggleRow("Session reminders", reminders) { enabled ->
                if (preview) reminders = enabled
                else savePreference(sessionRemindersKey, enabled)
            }
            Caption("Push and email reminders for your next Zoom session.")
            HorizontalDivider(color=Edge)
            ToggleRow("Request and booking updates", updates) { enabled ->
                if (preview) updates = enabled
                else savePreference(bookingUpdatesKey, enabled)
            }
            Caption("Booking confirmations and accepted or rejected requests.")
        }
        errorMessage?.let { Caption(it) }
        Heading("Account settings",18)
        Panel {
            Detail("✉","Email",email)
            SettingsRow("Password", "Update your account password") {
                passwordChanged = false
                showPasswordDialog = true
            }
            if (passwordChanged) Caption("Password updated successfully.")
            HorizontalDivider(color=Edge)
            SettingsRow("Privacy", "View how Jeel handles your data") { go("privacy") }
        }
        DangerButton("Logout") { confirmation="logout" }
        Caption("Jeel 1.0 · Guidance for every generation")
    }
}

@Composable
private fun ChangePasswordDialog(
    onChangePassword: suspend (String, String) -> Boolean,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var current by remember { mutableStateOf("") }
    var replacement by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = { Text("Change password") },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Field("Current password", current, { current = it }, secret = true)
                Field("New password", replacement, { replacement = it }, secret = true)
                Field("Confirm new password", confirmation, { confirmation = it }, secret = true)
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !saving,
                onClick = {
                    error = when {
                        current.isEmpty() -> "Enter your current password."
                        replacement.length < 8 -> "Use at least 8 characters."
                        replacement != confirmation -> "The new passwords do not match."
                        replacement == current -> "Choose a different new password."
                        else -> null
                    }
                    if (error == null) {
                        val oldPassword = current
                        val newPassword = replacement
                        saving = true
                        scope.launch {
                            try {
                                if (onChangePassword(oldPassword, newPassword)) onSuccess()
                                else error = "Could not update your password. Check your current password and try again."
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (_: Exception) {
                                error = "Could not save the password. Please try again."
                            } finally {
                                saving = false
                            }
                        }
                    }
                }
            ) { Text(if (saving) "Saving…" else "Save password") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) { Text("Cancel") }
        }
    )
}

@Composable
internal fun ColumnScope.PrivacyScreen(onBack: () -> Unit) {
    PageHead("JEEL", "Privacy & Data", onBack)
    Panel {
        Heading("Account and profile", 18)
        BodyText("JEEL stores account information and profile details on this device. You can review and update your details in My Profile.")
        HorizontalDivider(color = Edge)
        Heading("Mentorship activity", 18)
        BodyText("JEEL saves booking details, meeting links, session status and ratings to support mentorship sessions.")
        HorizontalDivider(color = Edge)
        Heading("Notification preferences", 18)
        BodyText("JEEL saves your notification choices on this device. You can change them in Settings.")
    }
}
