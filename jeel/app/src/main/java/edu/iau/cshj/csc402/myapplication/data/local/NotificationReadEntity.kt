package edu.iau.cshj.csc402.myapplication.data.local

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "notification_reads", primaryKeys = ["userId", "eventKey"],
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["userId"],
        childColumns = ["userId"], onDelete = ForeignKey.CASCADE)])
data class NotificationReadEntity(val userId: Int, val eventKey: String)
