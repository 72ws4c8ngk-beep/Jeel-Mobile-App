package edu.iau.cshj.csc402.myapplication.ui.screens.mentee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import edu.iau.cshj.csc402.myapplication.data.JeelRepository
import edu.iau.cshj.csc402.myapplication.data.local.SessionEntity
import edu.iau.cshj.csc402.myapplication.ui.components.*
import kotlinx.coroutines.launch

@Composable
internal fun ColumnScope.ProgressScreen(
    repository: JeelRepository,
    menteeId: Int?,
    sessions: List<SessionEntity>
) {
    PageHead("YOUR PROGRESS", "Look how far you've come")
    if (menteeId == null) {
        BodyText("Progress is unavailable.")
        return
    }

    val goals by remember(repository, menteeId) {
        repository.observeGoals(menteeId)
    }.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var addingGoal by remember(menteeId) { mutableStateOf(false) }
    var actionGoalId by remember(menteeId) { mutableStateOf<Int?>(null) }
    var saving by remember(menteeId) { mutableStateOf(false) }
    var error by remember(menteeId) { mutableStateOf(false) }

    fun save(write: suspend () -> Boolean) {
        if (saving) return
        saving = true
        scope.launch {
            try {
                error = !write()
                if (!error) {
                    addingGoal = false
                    actionGoalId = null
                }
            } catch (cancelled: kotlinx.coroutines.CancellationException) {
                throw cancelled
            } catch (_: android.database.SQLException) {
                error = true
            } finally {
                saving = false
            }
        }
    }

    val loadedGoals = goals
    val bookedSessions = sessions
        .filter { it.menteeId == menteeId }
        .sortedByDescending { it.sessionId }
    val completedSessions = bookedSessions.count { it.status == "Completed" }
    val totalTasks = loadedGoals.orEmpty().sumOf { it.actions.size }
    val completedTasks = loadedGoals.orEmpty().sumOf { goal ->
        goal.actions.count { it.isCompleted }
    }
    val overallProgress = when {
        loadedGoals == null -> "…"
        totalTasks == 0 -> "0%"
        else -> "${completedTasks * 100L / totalTasks}%"
    }
    Panel {
        Stats(listOf(
            overallProgress to "Overall progress",
            completedSessions.toString() to "Sessions completed"
        ))
    }

    Section("Your learning goals", "Add goal") { addingGoal = true }
    if (error) BodyText("Could not save. Please try again.")
    if (loadedGoals == null) {
        CircularProgressIndicator()
    } else if (loadedGoals.isEmpty()) {
        BodyText("Add a goal to start tracking your progress.")
    }
    loadedGoals.orEmpty().forEach { (goal, actions) ->
        key(goal.goalId) {
            val completed = actions.count { it.isCompleted }
            val progress = if (actions.isEmpty()) 0f else completed.toFloat() / actions.size
            ProgressCard(goal.title, progress, "$completed of ${actions.size} tasks complete")
            Panel {
                if (goal.description.isNotBlank()) BodyText(goal.description)
                actions.sortedBy { it.actionId }.forEach { action ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = action.isCompleted,
                            enabled = !saving,
                            onCheckedChange = { checked ->
                                save { repository.setActionCompleted(menteeId, action.actionId, checked) }
                            }
                        )
                        BodyText(action.title)
                    }
                }
                TextButton(onClick = { actionGoalId = goal.goalId }, enabled = !saving) {
                    Text("Add task")
                }
            }
        }
    }

    Heading("Your journey", 18)
    if (bookedSessions.isEmpty()) {
        BodyText("Your booked sessions will appear here.")
    }
    bookedSessions.forEach { session ->
        Panel {
            BodyText("${session.sessionDate} • ${session.sessionTime}")
            Caption("Mentorship • ${session.status}")
        }
    }

    val goalId = actionGoalId
    if (addingGoal || goalId != null) {
        var title by remember(goalId) { mutableStateOf("") }
        var description by remember(goalId) { mutableStateOf("") }
        val dismiss = {
            if (!saving) {
                addingGoal = false
                actionGoalId = null
                error = false
            }
        }
        AlertDialog(
            onDismissRequest = dismiss,
            title = { Text(if (goalId == null) "Add learning goal" else "Add task") },
            text = {
                Column {
                    OutlinedTextField(title, { title = it }, label = { Text("Title") }, singleLine = true)
                    if (goalId == null) {
                        OutlinedTextField(description, { description = it }, label = { Text("Description (optional)") })
                    }
                    if (error) Text("Could not save. Please try again.")
                }
            },
            confirmButton = {
                TextButton(
                    enabled = title.isNotBlank() && !saving,
                    onClick = {
                        save {
                            if (goalId == null) repository.addGoal(menteeId, title, description)
                            else repository.addAction(menteeId, goalId, title)
                        }
                    }
                ) { Text("Add") }
            },
            dismissButton = { TextButton(onClick = dismiss, enabled = !saving) { Text("Cancel") } }
        )
    }
}
