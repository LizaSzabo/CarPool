package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class AddAnnouncementPresenter @Inject constructor(
    private val announcementInteractor: AnnouncementInteractor,
    private val userInteractor: UserInteractor,
) {


    suspend fun saveNewAnnouncement(announcement: Announcement): String {
        var saveAnnouncementResult = ""
        announcementInteractor.saveNewAnnouncement(announcement).collect { state ->
            saveAnnouncementResult = when (state) {
                is AnnouncementHandlingState.Loading -> {
                    "saving"
                }
                is AnnouncementHandlingState.Success ->
                    "success"
                is AnnouncementHandlingState.Failed -> {
                    "error"
                }
            }
        }
        return saveAnnouncementResult
    }

    suspend fun addAnnouncementToItsUser(announcementId: String): String {
        var addAnnouncementResult = ""
        currentUser.email?.let {
            userInteractor.addAnnouncementToItsUser(it, announcementId)
                .collect { state ->
                    addAnnouncementResult = when (state) {
                        is BackendHandleState.Loading -> {
                            "saving"
                        }
                        is BackendHandleState.Success -> {
                            "success"
                        }
                        is BackendHandleState.Failed -> {
                            "error"
                        }
                    }
                }
        }
        return addAnnouncementResult
    }
}