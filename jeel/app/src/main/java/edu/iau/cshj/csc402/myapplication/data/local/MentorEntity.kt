package edu.iau.cshj.csc402.myapplication.data.local




import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mentors",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"], unique = true)]
)
data class MentorEntity(
    @PrimaryKey(autoGenerate = true)
    val mentorId: Int = 0,

    val userId: Int,
    val specialization: String,
    val yearsOfExperience: Int = 0,
    val rating: Double = 0.0
)
