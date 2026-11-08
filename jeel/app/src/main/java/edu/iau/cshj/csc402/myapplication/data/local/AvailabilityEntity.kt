package edu.iau.cshj.csc402.myapplication.data.local



import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "availability",
    foreignKeys = [
        ForeignKey(
            entity = MentorEntity::class,
            parentColumns = ["mentorId"],
            childColumns = ["mentorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["mentorId"])
    ]
)
data class AvailabilityEntity(
    @PrimaryKey(autoGenerate = true)
    val availabilityId: Int = 0,
    val mentorId: Int,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String
)
