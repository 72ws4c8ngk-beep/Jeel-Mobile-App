
package edu.iau.cshj.csc402.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface JeelDao {

    // USERS

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)


    // MENTORS

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMentor(mentor: MentorEntity): Long

    @Query("SELECT * FROM mentors WHERE userId = :userId LIMIT 1")
    suspend fun getMentorByUserId(userId: Int): MentorEntity?

    @Query("SELECT * FROM mentors")
    fun getAllMentors(): Flow<List<MentorEntity>>

    @Query("""
    SELECT
        m.mentorId,
        m.userId,
        u.fullName,
        m.specialization,
        m.yearsOfExperience,
        m.rating,
        u.bio,
        u.profileData,
        EXISTS(SELECT 1 FROM availability AS a WHERE a.mentorId = m.mentorId) AS hasAvailability
    FROM mentors AS m
    INNER JOIN users AS u ON m.userId = u.userId
    """)
    fun getMentorProfiles(): Flow<List<MentorProfile>>

    @Query("SELECT * FROM mentors WHERE mentorId = :mentorId")
    suspend fun getMentorById(mentorId: Int): MentorEntity?

    @Update
    suspend fun updateMentor(mentor: MentorEntity)


    // SESSIONS

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSession(session: SessionEntity): Long
    @Query("""
    SELECT COUNT(*) FROM sessions
    WHERE mentorId = :mentorId
    AND sessionDate = :sessionDate
    AND sessionTime = :sessionTime
    AND status IN ('Pending', 'Accepted')
""")
    suspend fun countExistingBookings(
        mentorId: Int,
        sessionDate: String,
        sessionTime: String
    ): Int
    @Query("SELECT * FROM sessions WHERE menteeId = :menteeId")
    fun getSessionsByMentee(
        menteeId: Int
    ): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE mentorId = :mentorId")
    fun getSessionsByMentor(
        mentorId: Int
    ): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE sessionId = :sessionId")
    suspend fun getSessionById(
        sessionId: Int
    ): SessionEntity?

    @Update
    suspend fun updateSession(session: SessionEntity)


    @Query("UPDATE sessions SET status = 'Completed' WHERE sessionId = :id AND mentorId = :mentorId AND status = 'Accepted'")
    suspend fun completeSession(id: Int, mentorId: Int): Int

    @Query("UPDATE sessions SET status = 'Cancelled' WHERE sessionId = :id AND (menteeId = :menteeId OR mentorId = :mentorId) AND status IN ('Pending', 'Accepted')")
    suspend fun cancelSession(id: Int, menteeId: Int?, mentorId: Int?): Int

    @Query("UPDATE sessions SET rating = :rating, feedback = :feedback WHERE sessionId = :id AND menteeId = :menteeId AND status = 'Completed' AND rating IS NULL")
    suspend fun submitReview(id: Int, menteeId: Int, rating: Int, feedback: String): Int

    // MENTOR AVAILABILITY

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAvailability(
        availability: AvailabilityEntity
    ): Long

    @Query("""
        SELECT * FROM availability
        WHERE mentorId = :mentorId
        ORDER BY dayOfWeek, startTime
    """)
    fun getAvailabilityByMentor(
        mentorId: Int
    ): Flow<List<AvailabilityEntity>>

    @Update
    suspend fun updateAvailability(
        availability: AvailabilityEntity
    )

    @Delete
    suspend fun deleteAvailability(
        availability: AvailabilityEntity
    )
}
