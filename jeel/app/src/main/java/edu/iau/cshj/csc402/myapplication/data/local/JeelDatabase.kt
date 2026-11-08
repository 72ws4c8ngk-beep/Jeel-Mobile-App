
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
        AvailabilityEntity::class
    ],
    version = 4,
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
                        MIGRATION_3_4
                    )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
