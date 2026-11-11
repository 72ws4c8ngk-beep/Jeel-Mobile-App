package edu.iau.cshj.csc402.myapplication.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

import edu.iau.cshj.csc402.myapplication.ui.screens.mentee.*
import edu.iau.cshj.csc402.myapplication.ui.screens.mentor.*
import edu.iau.cshj.csc402.myapplication.ui.screens.shared.*
import edu.iau.cshj.csc402.myapplication.data.local.MentorProfile
import kotlinx.coroutines.launch
import edu.iau.cshj.csc402.myapplication.data.local.AvailabilityEntity
import edu.iau.cshj.csc402.myapplication.data.local.SessionEntity
private fun timeToMinutes(time: String): Int? {
    return try {
        val parts = time.trim().uppercase().split(" ")
        val clock = parts[0].split(":")
        val hour = clock[0].toInt()
        val minute = clock[1].toInt()
        val period = parts[1]

        if (hour !in 1..12 || minute !in 0..59 ||
            period !in listOf("AM", "PM")
        ) return null

        (hour % 12) * 60 + minute +
                if (period == "PM") 720 else 0
    } catch (e: Exception) {
        null
    }
}
@Composable
fun JeelWorkspace(
    initialRole: String,
    initialName: String,
    initialEmail: String,
    initialRoute: String = "home",
    initialProfile: Map<String, String> = emptyMap(),
    mentorProfiles: List<MentorProfile> = emptyList(),
    onProfileSaved: (String, String, Map<String, String>) -> Unit = { _, _, _ -> },
    logout: () -> Unit
) {
    val scene = LocalUiScene.current
    val scope = rememberCoroutineScope()

    // MENTOR AVAILABILITY: Identify the logged-in mentor
    val context = LocalContext.current

    val repository = remember(context) {
        JeelRepository.getRepository(context)
    }

    var currentMentorId by remember(initialEmail) {
        mutableStateOf<Int?>(null)
    }
    var currentMenteeId by remember(initialEmail) {
        mutableStateOf<Int?>(null)
    }

    LaunchedEffect(initialEmail, initialRole) {
        currentMenteeId = null

        if (initialRole == "Mentee" && initialEmail.isNotBlank()) {
            val user = repository.getUserByEmail(
                initialEmail.trim().lowercase()
            )

            if (user != null && user.role == "Mentee") {
                currentMenteeId = user.userId
            }
        }
    }

    LaunchedEffect(initialEmail, initialRole) {
        currentMentorId = null

        if (initialRole == "Mentor") {
            val user = repository.getUserByEmail(
                initialEmail.trim().lowercase()
            )

            if (user != null && user.role == "Mentor") {
                currentMentorId =
                    repository.getMentorByUserId(user.userId)?.mentorId
            }
        }
    }

    val state = remember(initialRole, initialRoute, scene) {
        JeelUiState(
            initialRole,
            initialName,
            initialEmail,
            initialRoute,
            initialProfile
        ).apply {
            when (scene.state) {
                "Completed tab" -> sessionTab = "Completed"
                "Cancelled tab" -> {
                    sessionTab = "Cancelled"
                    cancelled = true
                }

                "Accepted tab" -> {
                    requestTab = "Accepted"
                    requestStates = listOf("Accepted", "Accepted")
                }

                "Rejected tab" -> {
                    requestTab = "Rejected"
                    requestStates = listOf("Rejected", "Rejected")
                }

                "Accepted" -> requestStates = listOf("Accepted", "Pending")
                "Rejected" -> requestStates = listOf("Rejected", "Pending")
                "Rating selected" -> rating = 5
                "Read" -> allRead = true
                "Arabic selected" -> language = "العربية"
                "Notifications off" -> {
                    reminders = false
                    updates = false
                }

                "Reschedule", "Reschedule confirmed" -> rescheduling = true
                "Awaiting approval" -> bookingPending = true
                "No slot selected" -> {
                    date = 0
                    time = ""
                }

                "Search results" -> query = "Product strategy"
                "No filters", "Reset" -> {
                    filterArea = "All expertise"
                    filterSkills = "Any"
                    filterAvailability = "Any time"
                }

                "Add slot", "Edit slot", "Invalid time", "Overlapping slots" -> {
                    slotDialog = true
                    editSlot = if (scene.state == "Edit slot") 0 else -1
                    slotText =
                        if (scene.state == "Invalid time") "11:45 AM"
                        else "11:00 AM"
                }

                "Confirm cancellation" -> confirmation = "cancel"
                "Confirm acceptance" -> confirmation = "accept"
                "Confirm rejection" -> confirmation = "reject"
                "Confirm deletion" -> confirmation = "delete"
                "Confirm logout" -> confirmation = "logout"
                "Discard changes" -> confirmation = "discard"
            }
        }
    }
    var currentUserId by remember(initialEmail) { mutableStateOf<Int?>(null) }
    LaunchedEffect(initialEmail, initialRole) {
        currentUserId = repository.getUserByEmail(initialEmail.trim().lowercase())?.takeIf { it.role == initialRole }?.userId
    }
    LaunchedEffect(currentUserId, state) {
        val id = currentUserId ?: return@LaunchedEffect
        repository.observeUser(id).collect { user ->
            if (user != null) {
                val json = runCatching { org.json.JSONObject(user.profileData) }.getOrDefault(org.json.JSONObject())
                val values = json.keys().asSequence().associateWith { json.optString(it) }
                state.applyProfile(values, user.fullName, user.email)
            }
        }
    }
    LaunchedEffect(currentUserId, state) {
        val id = currentUserId ?: return@LaunchedEffect
        repository.observeReadNotifications(id).collect { state.readNotificationKeys = it }
    }
    state.markNotificationsRead = { keys ->
        currentUserId?.let { id -> scope.launch {
            try { repository.markNotificationsRead(id, keys) }
            catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                state.notice = "Unable to mark notifications as read. Please try again."
            }
        } }
    }
    state.saveProfile = { draft ->
        val id = currentUserId
        if (id != null && !state.profileSaving) {
            val values = draft.profileValues()
            val fullName = draft.name
            val accountEmail = draft.email
            state.profileSaving = true
            scope.launch {
                try {
                    repository.saveProfile(id, fullName, accountEmail, values)
                    state.applyProfile(values, fullName.trim(), accountEmail.trim().lowercase())
                    onProfileSaved(fullName.trim(), accountEmail.trim().lowercase(), values)
                    state.editSnapshot = emptyList()
                    state.tab("profile")
                    state.notice = "Profile saved."
                } catch (e: Exception) {
                    if (e is kotlinx.coroutines.CancellationException) throw e
                    state.notice = if (e is IllegalArgumentException) e.message.orEmpty() else "Unable to save profile. Please try again."
                } finally { state.profileSaving = false }
            }
        }
    }
    LaunchedEffect(currentMenteeId, state) {
        val menteeId = currentMenteeId

        if (menteeId == null) {
            state.menteeSessions = emptyList()
        } else {
            repository.getSessionsByMentee(menteeId).collect { sessions ->
                state.menteeSessions = sessions
            }
        }
    }
    LaunchedEffect(mentorProfiles) {
        state.mentorProfiles = mentorProfiles
    }
    LaunchedEffect(currentMentorId) {
        val mentorId = currentMentorId

        if (mentorId == null) {
            state.availabilitySlots = emptyList()
        } else {
            repository.getAvailabilityByMentor(mentorId).collect { slots ->
                state.availabilitySlots = slots
            }
        }
    }
    LaunchedEffect(currentMentorId) {
        val mentorId = currentMentorId

        if (mentorId == null) {
            state.mentorSessions = emptyList()
        } else {
            repository.getSessionsByMentor(mentorId).collect { sessions ->
                state.mentorSessions = sessions
                val names = mutableMapOf<Int, String>()

                sessions.forEach { session ->
                    val user = repository.getUserById(session.menteeId)
                    names[session.menteeId] = user?.fullName ?: "Unknown Mentee"
                }

                state.menteeNames = names
            }
        }
    }
    LaunchedEffect(state.selectedRoomMentorId) {
        val mentorId = state.selectedRoomMentorId

        if (mentorId == null) {
            state.bookingAvailabilitySlots = emptyList()
        } else {
            repository.getAvailabilityByMentor(mentorId).collect { slots ->
                state.bookingAvailabilitySlots = slots
            }
        }
    }
    fun saveSessionChange(action: suspend () -> Boolean, onSuccess: () -> Unit) {
        if (state.sessionSaving) return
        state.sessionSaving = true
        scope.launch {
            try {
                if (action()) onSuccess()
                else state.notice = "This session has changed. Please reopen it and try again."
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                state.notice = "Could not save your changes. Please try again."
            } finally {
                state.sessionSaving = false
            }
        }
    }
    state.completeSelectedSession = {
        val id = state.selectedSessionId
        val mentorId = currentMentorId
        if (mentorId != null) saveSessionChange(
            { repository.completeSession(id, mentorId) },
            { state.sessionTab = "Completed"; state.tab("sessions") }
        )
    }
    state.submitSelectedReview = {
        val id = state.selectedSessionId
        val menteeId = currentMenteeId
        val stars = state.rating
        val text = state.feedback
        if (menteeId != null && stars in 1..5) saveSessionChange(
            { repository.submitReview(id, menteeId, stars, text) },
            { state.sessionTab = "Completed"; state.tab("sessions") }
        )
    }
    with(state) {
        BackHandler {
            back()
        }

        JeelTheme {
            Scaffold(
                containerColor = Cream,
                contentWindowInsets = WindowInsets.safeDrawing,
                bottomBar = {
                    if (route in roots || route in listOf(
                            "session",
                            "request",
                            "notifications"
                        )
                    ) {
                        NavigationBar(
                            containerColor = Color.White,
                            tonalElevation = 0.dp
                        ) {
                            val tabs =
                                if (isMentor)
                                    listOf(
                                        "home" to "Home",
                                        "requests" to "Requests",
                                        "sessions" to "Sessions",
                                        "profile" to "Profile"
                                    )
                                else
                                    listOf(
                                        "home" to "Home",
                                        "explore" to "Explore",
                                        "sessions" to "Sessions",
                                        "progress" to "Progress",
                                        "profile" to "Profile"
                                    )

                            tabs.forEach { (key, label) ->
                                NavigationBarItem(
                                    selected =
                                        route == key ||
                                                (key == "sessions" && route == "session") ||
                                                (key == "requests" && route == "request"),
                                    onClick = {
                                        tab(key)
                                    },
                                    icon = {
                                        JeelOutlineIcon(key)
                                    },
                                    label = {
                                        Text(label, fontSize = 10.sp)
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Mist,
                                        selectedIconColor = Navy,
                                        selectedTextColor = Navy,
                                        unselectedIconColor = Slate,
                                        unselectedTextColor = Slate
                                    )
                                )
                            }
                        }
                    }
                }
            ) { padding ->
                key(route) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .imePadding()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                horizontal = 24.dp,
                                vertical = 16.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(22.dp)
                    ) {
                        if (notice.isNotBlank()) Text(notice, color = MaterialTheme.colorScheme.error)
                        UiStatePresentation {
                            when (route) {
                                "home" ->
                                    if (isMentor) MentorHomeScreen(state)
                                    else MenteeHomeScreen(state)

                                "explore" -> ExploreScreen(state)
                                "filters" -> FilterScreen(state)
                                "mentor" -> MentorDetailScreen(state)
                                "book" -> BookingScreen(
                                    state = state,
                                    onConfirmBooking = {
                                        val menteeId = currentMenteeId
                                        val mentorId = state.selectedRoomMentorId

                                        if (menteeId == null || mentorId == null) {
                                            state.notice = "Unable to book. Please select a mentor."
                                        } else {
                                            scope.launch {
                                                val selectedDate = "%04d-%02d-%02d".format(
                                                    state.year,
                                                    state.month,
                                                    state.date
                                                )

                                                val existingBookings = repository.countExistingBookings(
                                                    mentorId,
                                                    selectedDate,
                                                    state.time
                                                )

                                                if (existingBookings > 0) {
                                                    state.notice = "This time slot is already booked. Please choose another."
                                                } else {
                                                    repository.insertSession(
                                                        SessionEntity(
                                                            menteeId = menteeId,
                                                            mentorId = mentorId,
                                                            sessionDate = selectedDate,
                                                            sessionTime = state.time,
                                                            status = "Pending",
                                                            notes = state.bookingNote
                                                        )
                                                    )

                                                    state.bookingPending = true
                                                    state.go("confirmation")
                                                }
                                            }
                                        }
                                    }
                                )                                "confirmation" -> BookingConfirmationScreen(state)

                                "sessions" ->
                                    if (isMentor) MentorSessionsScreen(state)
                                    else MenteeSessionsScreen(state)

                                "session" -> SessionDetailsScreen(state)
                                "review" -> SessionReviewScreen(state)
                                "requests" -> RequestsScreen(state)
                                "request" -> RequestDetailScreen(state)
                                "availability" -> AvailabilityScreen(state)
                                "progress" -> ProgressScreen(state)
                                "profile" -> MyProfileScreen(state)

                                "editProfile" ->
                                    if (isMentor) MentorEditProfileScreen(state)
                                    else MenteeEditProfileScreen(state)

                                "notifications" -> NotificationsScreen(state)
                                "settings" -> SettingsScreen(state)
                            }
                        }

                        Spacer(Modifier.height(12.dp))
                    }
                }
            }

            if (confirmation.isNotEmpty()) {
                AlertDialog(
                    onDismissRequest = {
                        confirmation = ""
                    },
                    title = {
                        Text(
                            when (confirmation) {
                                "logout" -> "Log out of Jeel?"
                                "cancel" -> "Cancel this session?"
                                "accept" -> "Accept this request?"
                                "reject" -> "Reject this request?"
                                "delete" -> "Delete this time slot?"
                                "discard" -> "Discard your changes?"
                                else -> "Confirm this action?"
                            }
                        )
                    },
                    text = {
                        Text(
                            when (confirmation) {
                                "logout" ->
                                    "You can log in again at any time to continue your mentorship journey."

                                "cancel" ->
                                    "Both participants will be notified. This appointment will leave your upcoming sessions."

                                "accept" ->
                                    "An upcoming Zoom session will be created for this mentorship."

                                "reject" ->
                                    "The mentee will be notified of your decision."

                                "delete" ->
                                    "Mentees will no longer be able to book this available time. Existing bookings stay unchanged."

                                "discard" ->
                                    "Your unsaved edits will be lost. Your saved profile will stay as it was."

                                else ->
                                    "Review the details before continuing."
                            }
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                when (confirmation) {
                                    "logout" -> logout()
                                    "cancel" -> {
                                        val id = selectedSessionId
                                        val menteeId = currentMenteeId
                                        val mentorId = currentMentorId
                                        saveSessionChange(
                                            { repository.cancelSession(id, menteeId, mentorId) },
                                            { sessionTab = "Cancelled"; tab("sessions") }
                                        )
                                    }
                                    "reject", "accept" -> {
                                        val requestId = selectedRequestId
                                        val mentorId = currentMentorId
                                        val newStatus = if (confirmation == "accept") "Accepted" else "Rejected"

                                        val selectedSession = mentorSessions.firstOrNull {
                                            it.sessionId == requestId && it.mentorId == mentorId
                                        }

                                        if (selectedSession != null && selectedSession.status == "Pending") {
                                            scope.launch {
                                                val session = repository.getSessionById(requestId)

                                                if (session != null &&
                                                    session.mentorId == mentorId &&
                                                    session.status == "Pending"
                                                ) {
                                                    repository.updateSession(
                                                        session.copy(status = newStatus)
                                                    )
                                                }
                                            }
                                        }

                                        if (confirmation == "accept") {
                                            tab("sessions")
                                        } else {
                                            tab("requests")
                                        }
                                    }
                                    "delete" -> {
                                        val mentorId = currentMentorId
                                        val slotId = deletingSlotId

                                        val slot = availabilitySlots.firstOrNull {
                                            it.availabilityId == slotId &&
                                                    it.mentorId == mentorId
                                        }

                                        if (slot != null) {
                                            scope.launch {
                                                repository.deleteAvailability(slot)
                                            }
                                        }

                                        deletingSlotId = null
                                    }
                                    "discard" -> back()
                                }
                                confirmation = ""
                            }
                        ) {
                            Text(
                                when (confirmation) {
                                    "logout" -> "Log out"
                                    "cancel" -> "Cancel session"
                                    "accept" -> "Accept request"
                                    "reject" -> "Reject request"
                                    "delete" -> "Delete slot"
                                    "discard" -> "Discard changes"
                                    else -> "Confirm"
                                }
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                confirmation = ""
                            }
                        ) {
                            Text(
                                if (confirmation == "discard")
                                    "Keep editing"
                                else "Go back"
                            )
                        }
                    }
                )
            }

            if (slotDialog) {
                AlertDialog(
                    onDismissRequest = {
                        slotDialog = false
                    },
                    title = {
                        Text(
                            if (editSlot < 0)
                                "Add time slot"
                            else
                                "Edit time slot"
                        )
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Picker(
                                "Day",
                                selectedDay,
                                listOf(
                                    "Mon", "Tue", "Wed",
                                    "Thu", "Fri", "Sat", "Sun"
                                )
                            ) {
                                selectedDay = it
                            }

                            EditBox("Start time", slotText) {
                                slotText = it
                            }

                            EditBox("End time", slotEndText) {
                                slotEndText = it
                            }

                            Caption("Arabia Standard Time (UTC+3)")
                            if (notice.isNotEmpty()) {
                                Text(
                                    text = notice,
                                    color = Color.Red
                                )
                            }
                            if (scene.state == "Invalid time") {
                                Info(
                                    "Invalid time",
                                    "End time must be after start time."
                                )
                            }

                            if (scene.state == "Overlapping slots") {
                                Info(
                                    "Overlapping slots",
                                    "This time overlaps 11:00–11:45 AM. Choose another slot."
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val mentorId = currentMentorId

                                if (mentorId != null) {
                                    scope.launch {
                                        val start = timeToMinutes(slotText)
                                        val end = timeToMinutes(slotEndText)

                                        if (start == null || end == null || end <= start) {
                                            notice = "Invalid time. End time must be after start time."
                                            return@launch
                                        }
                                        val hasOverlap = availabilitySlots.any { slot ->
                                            val existingStart = timeToMinutes(slot.startTime)
                                            val existingEnd = timeToMinutes(slot.endTime)

                                            slot.dayOfWeek == selectedDay &&
                                                    slot.availabilityId != editSlot &&
                                                    existingStart != null &&
                                                    existingEnd != null &&
                                                    start < existingEnd &&
                                                    end > existingStart
                                        }

                                        if (hasOverlap) {
                                            notice = "This time overlaps an existing slot."
                                            return@launch
                                        }
                                        if (editSlot == -1) {
                                            repository.insertAvailability(
                                                AvailabilityEntity(
                                                    mentorId = mentorId,
                                                    dayOfWeek = selectedDay,
                                                    startTime = slotText,
                                                    endTime = slotEndText
                                                )
                                            )
                                        } else {
                                            val existingSlot = availabilitySlots.firstOrNull {
                                                it.availabilityId == editSlot &&
                                                        it.mentorId == mentorId
                                            }

                                            if (existingSlot != null) {
                                                repository.updateAvailability(
                                                    existingSlot.copy(
                                                        dayOfWeek = selectedDay,
                                                        startTime = slotText,
                                                        endTime = slotEndText
                                                    )
                                                )
                                            }
                                        }

                                        slotDialog = false
                                    }
                                }
                            }
                        ) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                slotDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (addGoalDialog) {
                AlertDialog(
                    onDismissRequest = {
                        addGoalDialog = false
                    },
                    title = {
                        Text("Add learning goal")
                    },
                    text = {
                        EditBox("Goal title", newGoal) {
                            newGoal = it
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                addGoalDialog = false
                            }
                        ) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                addGoalDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
