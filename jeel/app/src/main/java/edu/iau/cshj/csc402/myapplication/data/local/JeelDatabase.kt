package edu.iau.cshj.csc402.myapplication.data.local



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        MentorEntity::class,
        SessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class JeelDatabase : RoomDatabase() {

    abstract fun jeelDao(): JeelDao

    companion object {

        @Volatile
        private var INSTANCE: JeelDatabase? = null

        fun getDatabase(context: Context): JeelDatabase {
            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JeelDatabase::class.java,
                    "jeel_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
