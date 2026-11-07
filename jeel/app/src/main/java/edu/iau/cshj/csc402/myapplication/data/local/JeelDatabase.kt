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
        SessionEntity::class
    ],
    version = 3,
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

        fun getDatabase(context: Context): JeelDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JeelDatabase::class.java,
                    "jeel_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
