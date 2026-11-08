
package edu.iau.cshj.csc402.myapplication.data

import android.content.Context
import edu.iau.cshj.csc402.myapplication.data.local.JeelDao
import edu.iau.cshj.csc402.myapplication.data.local.JeelDatabase
import edu.iau.cshj.csc402.myapplication.data.local.MentorEntity
import edu.iau.cshj.csc402.myapplication.data.local.SessionEntity
import edu.iau.cshj.csc402.myapplication.data.local.UserEntity
import edu.iau.cshj.csc402.myapplication.data.local.MentorProfile
import edu.iau.cshj.csc402.myapplication.data.local.AvailabilityEntity
import kotlinx.coroutines.flow.Flow

/** Local persistence boundary. Suspend operations must be called from a coroutine. */
class JeelRepository(private val dao: JeelDao) {

    suspend fun insertUser(user: UserEntity): Long = dao.insertUser(user)
    suspend fun getUserById(userId: Int): UserEntity? = dao.getUserById(userId)
    suspend fun getUserByEmail(email: String): UserEntity? = dao.getUserByEmail(email)
    suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    suspend fun insertMentor(mentor: MentorEntity): Long = dao.insertMentor(mentor)
    fun getAllMentors(): Flow<List<MentorEntity>> = dao.getAllMentors()
    fun getMentorProfiles(): Flow<List<MentorProfile>> = dao.getMentorProfiles()
    suspend fun getMentorById(mentorId: Int): MentorEntity? = dao.getMentorById(mentorId)
    suspend fun getMentorByUserId(userId: Int): MentorEntity? = dao.getMentorByUserId(userId)
    suspend fun updateMentor(mentor: MentorEntity) = dao.updateMentor(mentor)

    suspend fun insertSession(session: SessionEntity): Long = dao.insertSession(session)
    suspend fun countExistingBookings(
        mentorId: Int,
        sessionDate: String,
        sessionTime: String
    ): Int = dao.countExistingBookings(
        mentorId,
        sessionDate,
        sessionTime
    )
    fun getSessionsByMentee(menteeId: Int): Flow<List<SessionEntity>> =
        dao.getSessionsByMentee(menteeId)
    fun getSessionsByMentor(mentorId: Int): Flow<List<SessionEntity>> =
        dao.getSessionsByMentor(mentorId)
    suspend fun getSessionById(sessionId: Int): SessionEntity? = dao.getSessionById(sessionId)
    suspend fun updateSession(session: SessionEntity) = dao.updateSession(session)

    // MENTOR AVAILABILITY

    suspend fun insertAvailability(availability: AvailabilityEntity): Long =
        dao.insertAvailability(availability)

    fun getAvailabilityByMentor(mentorId: Int): Flow<List<AvailabilityEntity>> =
        dao.getAvailabilityByMentor(mentorId)

    suspend fun updateAvailability(availability: AvailabilityEntity) =
        dao.updateAvailability(availability)

    suspend fun deleteAvailability(availability: AvailabilityEntity) =
        dao.deleteAvailability(availability)

    companion object {
        fun getRepository(context: Context): JeelRepository =
            JeelRepository(JeelDatabase.getDatabase(context).jeelDao())
    }
}
