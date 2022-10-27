package hu.bme.aut.android.carpool.domain

import hu.bme.aut.android.carpool.data.firebaserepository.AnnouncementRepository
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnnouncementInteractor @Inject constructor(
    private val announcementRepository: AnnouncementRepository
) {


    fun saveNewAnnouncement(announcement: Announcement) = flow {

        emit(AnnouncementHandlingState.loading())

        val postRef = announcementRepository.uploadAnnouncement(announcement)

        emit(AnnouncementHandlingState.success(postRef))

    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(AnnouncementHandlingState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}