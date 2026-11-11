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

    @Test fun completionAndFeedbackSurviveReopeningAndRejectInvalidActions() = runBlocking {
        val mentee = repository.insertUser(UserEntity(fullName = "Student", email = "review@example.com", role = "Mentee")).toInt()
        val user = repository.insertUser(UserEntity(fullName = "Guide", email = "guide@example.com", role = "Mentor")).toInt()
        val mentor = repository.insertMentor(MentorEntity(userId = user, specialization = "Android")).toInt()
        val id = repository.insertSession(SessionEntity(menteeId = mentee, mentorId = mentor, sessionDate = "2026-11-12", sessionTime = "10:00")).toInt()
        assertFalse(repository.completeSession(id, mentor))
        assertFalse(repository.submitReview(id, mentee, 5, "Too early"))
        repository.updateSession(repository.getSessionById(id)!!.copy(status = "Accepted"))
        assertFalse(repository.completeSession(id, mentor + 100))
        assertTrue(repository.completeSession(id, mentor))
        assertFalse(repository.cancelSession(id, mentee, null))
        assertFalse(repository.submitReview(id, mentee + 100, 5, "Wrong user"))
        for (invalid in listOf(0, 6)) {
            try { repository.submitReview(id, mentee, invalid, "Invalid"); fail("Invalid stars") }
            catch (expected: IllegalArgumentException) { }
        }
        assertTrue(repository.submitReview(id, mentee, 4, " Helpful session "))
        assertFalse(repository.submitReview(id, mentee, 1, "Duplicate"))
        database.close()
        openDatabase()
        val saved = repository.getSessionById(id)!!
        assertEquals("Completed", saved.status)
        assertEquals(4, saved.rating)
        assertEquals("Helpful session", saved.feedback)
        assertEquals(saved, repository.getSessionsByMentor(mentor).first().single())
        assertEquals(saved, repository.getSessionsByMentee(mentee).first().single())
    }

    @Test fun cancellationChecksOwnershipAndPersists() = runBlocking {
        val mentee = repository.insertUser(UserEntity(fullName = "Student", email = "cancel@example.com", role = "Mentee")).toInt()
        val user = repository.insertUser(UserEntity(fullName = "Guide", email = "cancelguide@example.com", role = "Mentor")).toInt()
        val mentor = repository.insertMentor(MentorEntity(userId = user, specialization = "Android")).toInt()
        for (status in listOf("Pending", "Accepted")) {
            val id = repository.insertSession(SessionEntity(menteeId = mentee, mentorId = mentor, sessionDate = "2026-11-12", sessionTime = status, status = status)).toInt()
            assertFalse(repository.cancelSession(id, mentee + 100, null))
            assertFalse(repository.cancelSession(id, null, mentor + 100))
            assertTrue(if (status == "Pending") repository.cancelSession(id, mentee, null) else repository.cancelSession(id, null, mentor))
            assertFalse(repository.completeSession(id, mentor))
            assertFalse(repository.submitReview(id, mentee, 5, "Cancelled"))
            assertEquals(0, repository.countExistingBookings(mentor, "2026-11-12", status))
        }
        database.close()
        openDatabase()
        assertTrue(repository.getSessionsByMentee(mentee).first().all { it.status == "Cancelled" })
    }
}
