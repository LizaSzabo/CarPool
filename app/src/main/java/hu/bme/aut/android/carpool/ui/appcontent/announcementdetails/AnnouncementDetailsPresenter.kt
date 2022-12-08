package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import hu.bme.aut.android.carpool.CarPoolApplication
import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class AnnouncementDetailsPresenter @Inject constructor(
    private val announcementInteractor: AnnouncementInteractor
) {

    suspend fun getAnnouncement(announcementId: String): Announcement? {
        var announcement: Announcement? = null
        announcementInteractor.getAnnouncement(announcementId).collect { state ->
            announcement = when (state) {
                is AnnouncementHandlingState.Loading -> {
                    null
                }
                is AnnouncementHandlingState.Success -> {
                    state.data
                }
                is AnnouncementHandlingState.Failed -> {
                    null
                }
            }
        }
        return announcement
    }

    suspend fun registerUserToAnnouncement(
        announcementId: String,
        userName: String,
        currentTakenSeats: Int,
        currentAvailableSeats: Int,
        registeredUsers: MutableList<String>,
    ): String {
        var updateMessage = "loading"

        registeredUsers.add(userName)

        announcementInteractor.updateAnnouncement(
            announcementId,
            registeredUsers,
            currentTakenSeats + 1,
            currentAvailableSeats - 1
        ).collect { state ->
            updateMessage = when (state) {
                is BackendHandleState.Loading -> {
                    "loading"
                }
                is BackendHandleState.Success -> {
                    CarPoolApplication.currentUser.group.add(userName)
                    "success"
                }
                is BackendHandleState.Failed -> {
                    "error: " + state.message
                }
            }
        }
        return updateMessage
    }


    suspend fun unregisterUserToAnnouncement(
        announcementId: String,
        userName: String,
        currentTakenSeats: Int,
        currentAvailableSeats: Int,
        registeredUsers: MutableList<String>,
    ): String {
        var updateMessage = "loading"

        registeredUsers.remove(userName)

        announcementInteractor.updateAnnouncement(
            announcementId,
            registeredUsers,
            currentTakenSeats - 1,
            currentAvailableSeats + 1
        ).collect { state ->
            updateMessage = when (state) {
                is BackendHandleState.Loading -> {
                    "loading"
                }
                is BackendHandleState.Success -> {
                    CarPoolApplication.currentUser.group.add(userName)
                    "success"
                }
                is BackendHandleState.Failed -> {
                    "error: " + state.message
                }
            }
        }
        return updateMessage
    }
}