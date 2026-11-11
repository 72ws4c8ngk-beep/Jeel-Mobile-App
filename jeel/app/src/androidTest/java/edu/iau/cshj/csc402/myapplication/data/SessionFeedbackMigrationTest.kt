package edu.iau.cshj.csc402.myapplication.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import edu.iau.cshj.csc402.myapplication.data.local.JeelDatabase
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionFeedbackMigrationTest {
    @Test fun upgradePreservesSessionAndInitializesFeedback() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val helper = FrameworkSQLiteOpenHelperFactory().create(
            SupportSQLiteOpenHelper.Configuration.builder(context)
                .callback(object : SupportSQLiteOpenHelper.Callback(4) {
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        db.execSQL("CREATE TABLE sessions (sessionId INTEGER PRIMARY KEY, status TEXT NOT NULL)")
                        db.execSQL("INSERT INTO sessions VALUES (7, 'Accepted')")
                    }
                    override fun onUpgrade(db: androidx.sqlite.db.SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {}
                }).build()
        )
        try {
            val db = helper.writableDatabase
            JeelDatabase.MIGRATION_4_5.migrate(db)
            db.query("SELECT sessionId, status, rating, feedback FROM sessions").use {
                assertTrue(it.moveToFirst())
                assertEquals(7, it.getInt(0))
                assertEquals("Accepted", it.getString(1))
                assertTrue(it.isNull(2))
                assertEquals("", it.getString(3))
            }
        } finally { helper.close() }
    }
}
