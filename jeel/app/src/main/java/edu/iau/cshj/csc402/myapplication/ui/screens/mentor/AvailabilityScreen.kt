
package edu.iau.cshj.csc402.myapplication.ui.screens.mentor

import androidx.compose.ui.tooling.preview.Preview
import edu.iau.cshj.csc402.myapplication.navigation.JeelWorkspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.AvailabilityScreen(state: JeelUiState) {
    with(state) {

        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        val daySlots = availabilitySlots.filter {
            it.dayOfWeek == selectedDay
        }.sortedBy { it.startTime }

        PageHead(
            "MANAGE AVAILABILITY",
            "Make room for mentorship",
            ::back
        )

        BodyText(
            "Choose a rhythm that works for you. Your availability repeats weekly."
        )

        Picker(
            "Time zone",
            "Arabia Standard Time (UTC+3)",
            listOf("Arabia Standard Time (UTC+3)")
        ) {}

        Heading("Weekly schedule preview", 18)

        Panel {
            BodyText("12–18 October 2026")

            Chips(days, selectedDay) {
                selectedDay = it
            }

            Caption(
                "$selectedDay selected · ${daySlots.size} available slots"
            )
        }

        Heading("Available days", 18)

        MultiChips(days, availableDays) {
            availableDays = it
            selectedDay = it.lastOrNull() ?: "Sun"
        }

        Heading("$selectedDay time slots", 18)

        if (selectedDay in availableDays) {

            if (daySlots.isEmpty()) {
                Info(
                    "No time slots yet",
                    "Add a time slot for $selectedDay."
                )
            }

            daySlots.forEach { slot ->
                Panel {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            BodyText(
                                "${slot.startTime} – ${slot.endTime}"
                            )
                            Caption("Available")
                        }

                        TextButton(
                            onClick = {
                                editSlot = slot.availabilityId
                                slotText = slot.startTime
                                slotEndText = slot.endTime
                                slotDialog = true
                            }
                        ) {
                            Text("Edit")
                        }

                        TextButton(
                            onClick = {
                                deletingSlotId = slot.availabilityId
                                confirmation = "delete"
                            }
                        ) {
                            Text(
                                "Delete",
                                color = Color(0xFFB36A60)
                            )
                        }
                    }
                }
            }

            Action(
                "+ Add new time slot",
                secondary = true
            ) {
                editSlot = -1
                slotText = "3:00 PM"
                slotEndText = "3:45 PM"
                slotDialog = true
                notice = "Add button clicked"
            }

        } else {
            Info(
                "No availability on $selectedDay",
                "Select this day above to add availability."
            )
        }

        Info(
            "Existing bookings stay in place",
            "Weekly availability changes apply only to new requests. Reschedule or cancel a booked session from its details."
        )

        Action("Save availability") {
            // Persistence will be connected in JeelWorkspace.
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun AvailabilityScreenPreview() {
    JeelWorkspace(
        "Mentor",
        "",
        "",
        initialRoute = "availability"
    ) {}
}
