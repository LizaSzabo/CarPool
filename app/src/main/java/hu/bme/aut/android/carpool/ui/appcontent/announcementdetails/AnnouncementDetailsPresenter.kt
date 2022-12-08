package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
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
}