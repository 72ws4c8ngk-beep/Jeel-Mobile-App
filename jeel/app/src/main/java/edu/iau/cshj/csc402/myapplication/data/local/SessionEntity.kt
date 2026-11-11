package edu.iau.cshj.csc402.myapplication.data.local



import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["menteeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MentorEntity::class,
            parentColumns = ["mentorId"],
            childColumns = ["mentorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["menteeId"]),
        Index(value = ["mentorId"])
    ]
)
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Int = 0,

    val menteeId: Int,
    val mentorId: Int,
    val sessionDate: String,
    val sessionTime: String,
    val status: String = "Pending",
    val notes: String = "",
    val rating: Int? = null,
    val feedback: String = ""
)
