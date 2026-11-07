package edu.iau.cshj.csc402.myapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["email"], unique = true)
    ]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    val fullName: String,

    val email: String,

    val role: String,

    @ColumnInfo(defaultValue = "''")
    val passwordHash: String = "",

    @ColumnInfo(defaultValue = "''")
    val passwordSalt: String = "",

    val bio: String = "",

    val experience: String = "",

    val profileData: String = ""
)