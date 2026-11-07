package edu.iau.cshj.csc402.myapplication.data.local

data class MentorProfile(
    val mentorId: Int,
    val userId: Int,
    val fullName: String,
    val specialization: String,
    val yearsOfExperience: Int,
    val rating: Double,
    val bio: String,
    val profileData: String
)