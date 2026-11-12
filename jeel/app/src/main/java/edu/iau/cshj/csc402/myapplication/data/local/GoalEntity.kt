package edu.iau.cshj.csc402.myapplication.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "goals",
    foreignKeys = [ForeignKey(UserEntity::class, ["userId"], ["menteeId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("menteeId")]
)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val goalId: Int = 0,
    val menteeId: Int,
    val title: String,
    val description: String,
    val createdAt: Long
)

@Entity(
    tableName = "action_items",
    foreignKeys = [ForeignKey(GoalEntity::class, ["goalId"], ["goalId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("goalId")]
)
data class ActionItemEntity(
    @PrimaryKey(autoGenerate = true) val actionId: Int = 0,
    val goalId: Int,
    val title: String,
    val isCompleted: Boolean = false
)

data class GoalWithActions(
    @androidx.room.Embedded val goal: GoalEntity,
    @androidx.room.Relation(parentColumn = "goalId", entityColumn = "goalId")
    val actions: List<ActionItemEntity>
)
