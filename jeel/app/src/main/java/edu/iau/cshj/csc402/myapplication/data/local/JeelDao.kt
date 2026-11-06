package edu.iau.cshj.csc402.myapplication.data.local



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM mentors")
    fun getAllMentors(): Flow<List<MentorEntity>>

    @Query("SELECT * FROM mentors WHERE mentorId = :mentorId")
    suspend fun getMentorById(mentorId: Int): MentorEntity?

    @Update
    suspend fun updateMentor(mentor: MentorEntity)


    // SESSIONS

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSession(session: SessionEntity): Long

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
}
