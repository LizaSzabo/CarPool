package hu.bme.aut.android.carpool.domain

import com.google.firebase.firestore.ktx.toObjects
import hu.bme.aut.android.carpool.data.firebaserepository.AnnouncementRepository
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnnouncementInteractor @Inject constructor(
    private val announcementRepository: AnnouncementRepository
) {

    fun saveNewAnnouncement(announcement: Announcement) = flow {
        emit(AnnouncementHandlingState.loading())
        val announcementRef = announcementRepository.uploadAnnouncement(announcement)
        emit(AnnouncementHandlingState.success(announcementRef))
    }.catch {
        emit(AnnouncementHandlingState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getTodayAnnouncements() = flow<AnnouncementHandlingState<List<Announcement>>> {
        emit(AnnouncementHandlingState.loading())
        val snapshot = announcementRepository.getTodayAnnouncements().get().await()
        val announcements: List<Announcement> = snapshot.toObjects()
        emit(AnnouncementHandlingState.success(announcements))
    }.catch {
        emit(AnnouncementHandlingState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


}