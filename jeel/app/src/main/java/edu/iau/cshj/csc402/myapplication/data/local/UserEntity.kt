
package edu.iau.cshj.csc402.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val fullName: String,
    val email: String,
    val role: String,
    val bio: String = "",
    val experience: String = ""
)
