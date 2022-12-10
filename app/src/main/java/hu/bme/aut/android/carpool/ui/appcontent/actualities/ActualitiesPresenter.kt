package hu.bme.aut.android.carpool.ui.appcontent.actualities

import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.domain.AnnouncementInteractor
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class ActualitiesPresenter @Inject constructor(
    private val announcementInteractor: AnnouncementInteractor,
    private val userInteractor: UserInteractor
) {

    suspend fun getTodayAnnouncement(): List<Announcement?>? {
        var announcements: List<Announcement?>? = null
        announcementInteractor.getTodayAnnouncements().collect { state ->
            announcements = when (state) {
                is AnnouncementHandlingState.Loading -> {
                   null
                }
                is AnnouncementHandlingState.Success -> {
                   state.data.filter { announcement ->
                        announcement.owner?.name?.let { userName ->
                            currentUser.name?.let { currentUserName ->
                                getUserGroup(userName)?.contains(
                                    currentUserName
                                )
                            }
                        } == true
                    }
                }
                is AnnouncementHandlingState.Failed -> {
                    null
                }
            }
        }
        return announcements
    }

    private suspend fun getUserGroup(userName: String): MutableList<String>? {
        var group: MutableList<String>? = mutableListOf()
        userInteractor.getUserByName(userName).collect { state ->
            group = when (state) {
                is BackendHandleState.Loading -> {
                    mutableListOf()
                }
                is BackendHandleState.Success -> {
                    state.data.group
                }
                is BackendHandleState.Failed -> {
                    null
                }
            }
        }
        return group
    }
}
