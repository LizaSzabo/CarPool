package hu.bme.aut.android.carpool.ui.appcontent.actualities

import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ActualitiesPresenter @Inject constructor(
    private val announcementInteractor: AnnouncementInteractor
) {

    suspend fun getTodayAnnouncement(): List<Announcement?>? {
        var announcements: List<Announcement?>? = null
        announcementInteractor.getTodayAnnouncements().collect { state ->
            announcements = when (state) {
                is AnnouncementHandlingState.Loading -> {
                    emptyList()
                }
                is AnnouncementHandlingState.Success -> {
                    state.data
                }
                is AnnouncementHandlingState.Failed -> {
                    null
                }
            }
        }
        return announcements
    }
}
