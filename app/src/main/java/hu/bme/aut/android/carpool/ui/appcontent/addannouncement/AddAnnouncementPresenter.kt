package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import javax.inject.Inject

class AddAnnouncementPresenter @Inject constructor(
    private val announcementInteractor: AnnouncementInteractor
) {


    suspend fun saveNewAnnouncement(announcement: Announcement): Int {
        var number = 1
        announcementInteractor.saveNewAnnouncement(announcement).collect { state ->
            number = when (state) {
                is AnnouncementHandlingState.Loading -> {
                    1
                }
                is AnnouncementHandlingState.Success -> {
                    2
                }
                is AnnouncementHandlingState.Failed -> {
                    3
                }
            }
        }
        return number
    }
}