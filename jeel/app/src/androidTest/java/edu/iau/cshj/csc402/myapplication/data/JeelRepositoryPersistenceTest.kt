package edu.iau.cshj.csc402.myapplication.data

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.iau.cshj.csc402.myapplication.data.local.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JeelRepositoryPersistenceTest {
    private lateinit var context: Context
    private lateinit var database: JeelDatabase
    private lateinit var repository: JeelRepository
    private val databaseName = "jeel-persistence-test"

    @Before fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase(databaseName)
        openDatabase()
    }

    private fun openDatabase() {
        database = Room.databaseBuilder(context, JeelDatabase::class.java, databaseName).build()
        repository = JeelRepository(database.jeelDao())
    }

    @After fun tearDown() {
        database.close()
        context.deleteDatabase(databaseName)
    }

    @Test fun recordsAndUpdatesSurviveClosingAndReopeningDatabase() = runBlocking {
        withTimeout(15_000) {
            val menteeId = repository.insertUser(UserEntity(fullName = "أصايل", email = "mentee@test.example", role = "Mentee")).toInt()
            val userId = repository.insertUser(UserEntity(fullName = "Mentor", email = "mentor@test.example", role = "Mentor")).toInt()
            val mentorId = repository.insertMentor(MentorEntity(userId = userId, specialization = "Android")).toInt()
            val sessionId = repository.insertSession(SessionEntity(menteeId = menteeId, mentorId = mentorId, sessionDate = "2026-11-06", sessionTime = "10:00")).toInt()
            assertTrue(menteeId > 0 && mentorId > 0 && sessionId > 0)
            repository.updateUser(repository.getUserById(menteeId)!!.copy(bio = "Updated bio"))
            repository.updateMentor(repository.getMentorById(mentorId)!!.copy(yearsOfExperience = 7, rating = 4.5))
            repository.updateSession(repository.getSessionById(sessionId)!!.copy(status = "Confirmed", notes = "Career plan"))
            database.close()
            openDatabase()
            val user = repository.getUserByEmail("mentee@test.example")!!
            assertEquals(menteeId, user.userId)
            assertEquals("أصايل", user.fullName)
            assertEquals("Updated bio", user.bio)
            assertEquals(7, repository.getMentorById(mentorId)!!.yearsOfExperience)
            assertEquals(4.5, repository.getAllMentors().first().single().rating, 0.0)
            val session = repository.getSessionById(sessionId)!!
            assertEquals("Confirmed", session.status)
            assertEquals("Career plan", session.notes)
            assertEquals(listOf(session), repository.getSessionsByMentee(menteeId).first())
            assertEquals(listOf(session), repository.getSessionsByMentor(mentorId).first())
            assertTrue(repository.getSessionsByMentee(-1).first().isEmpty())
            assertNull(repository.getUserById(-1))
            assertNull(repository.getMentorById(-1))
            assertNull(repository.getSessionById(-1))
        }
    }

    @Test fun missingParentIsRejectedWithoutSavingSession() = runBlocking {
        withTimeout(15_000) {
            try {
                repository.insertSession(SessionEntity(menteeId = 999, mentorId = 999, sessionDate = "2026-11-06", sessionTime = "10:00"))
                fail("A session must reference existing users and mentors")
            } catch (expected: SQLiteConstraintException) {
                assertTrue(repository.getSessionsByMentee(999).first().isEmpty())
            }
        }
    }
}
