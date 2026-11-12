
package edu.iau.cshj.csc402.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        UserEntity::class,
        MentorEntity::class,
        SessionEntity::class,
        AvailabilityEntity::class,
        NotificationReadEntity::class,
        GoalEntity::class,
        ActionItemEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class JeelDatabase : RoomDatabase() {

    abstract fun jeelDao(): JeelDao

    companion object {

        @Volatile
        private var INSTANCE: JeelDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    "ALTER TABLE users ADD COLUMN passwordHash TEXT NOT NULL DEFAULT ''"
                )

                db.execSQL(
                    "ALTER TABLE users ADD COLUMN passwordSalt TEXT NOT NULL DEFAULT ''"
                )

                db.execSQL(
                    "CREATE UNIQUE INDEX IF NOT EXISTS index_users_email ON users(email)"
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE users ADD COLUMN profileData TEXT NOT NULL DEFAULT ''"
                )
            }
        }

        // Mentor Availability: migration from version 3 to 4
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `availability` (
                        `availabilityId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `mentorId` INTEGER NOT NULL,
                        `dayOfWeek` TEXT NOT NULL,
                        `startTime` TEXT NOT NULL,
                        `endTime` TEXT NOT NULL,
                        FOREIGN KEY(`mentorId`)
                            REFERENCES `mentors`(`mentorId`)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE INDEX IF NOT EXISTS `index_availability_mentorId`
                    ON `availability` (`mentorId`)
                    """.trimIndent()
                )
            }
        }

        internal val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE sessions ADD COLUMN rating INTEGER")
                db.execSQL("ALTER TABLE sessions ADD COLUMN feedback TEXT NOT NULL DEFAULT ''")
            }
        }

        internal val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS notification_reads (userId INTEGER NOT NULL, eventKey TEXT NOT NULL, PRIMARY KEY(userId, eventKey), FOREIGN KEY(userId) REFERENCES users(userId) ON UPDATE NO ACTION ON DELETE CASCADE)")
            }
        }

        internal val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE goals (
                        goalId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        menteeId INTEGER NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        createdAt INTEGER NOT NULL,
                        FOREIGN KEY(menteeId) REFERENCES users(userId) ON DELETE CASCADE
                    )
                """.trimIndent())
                db.execSQL("CREATE INDEX index_goals_menteeId ON goals(menteeId)")
                db.execSQL("""
                    CREATE TABLE action_items (
                        actionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        goalId INTEGER NOT NULL,
                        title TEXT NOT NULL,
                        isCompleted INTEGER NOT NULL,
                        FOREIGN KEY(goalId) REFERENCES goals(goalId) ON DELETE CASCADE
                    )
                """.trimIndent())
                db.execSQL("CREATE INDEX index_action_items_goalId ON action_items(goalId)")
            }
        }

        fun getDatabase(context: Context): JeelDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JeelDatabase::class.java,
                    "jeel_database"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_5_6,
                        MIGRATION_6_7
                    )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
