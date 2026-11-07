package edu.iau.cshj.csc402.myapplication.data

import edu.iau.cshj.csc402.myapplication.R
import edu.iau.cshj.csc402.myapplication.data.local.MentorProfile
import org.json.JSONObject

internal fun MentorProfile.toDisplayMentor(): Mentor {

    val details = runCatching {
        JSONObject(profileData)
    }.getOrNull()

    val topics = details?.optString("topics").orEmpty()
        .split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    return Mentor(
        name = fullName,
        job = details?.optString("jobTitle")
            ?.ifBlank { "Mentor" } ?: "Mentor",
        area = specialization,
        years = yearsOfExperience,
        rating = rating.toString(),
        image = details?.optString("photo")
            ?.toIntOrNull() ?: R.drawable.jeel_omar,
        tags = topics,
        available = false
    )
}