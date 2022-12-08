package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.domain.model.Announcement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementDetailsViewModel @Inject constructor(
    private val announcementDetailsPresenter: AnnouncementDetailsPresenter
) : RainbowCakeViewModel<AnnouncementDetailsViewState>(Initial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun loadAnnouncementDetails(announcementId: String) {
        viewState = LoadingData

        scope.launch {
            val announcement = announcementDetailsPresenter.getAnnouncement(announcementId)

            viewState = when (announcement) {
                null -> LoadingError("Network error")
                else -> DataLoaded(announcement)
            }
        }
    }

    fun checkUserAlreadyRegistered(announcement: Announcement): Boolean {
        return (announcement.registeredUsers.contains(currentUser.name))
    }

    fun registerUserToAnnouncement(
        announcementId: String,
        currentTakenSeats: Int,
        currentAvailableSeats: Int,
        registeredUsers: MutableList<String>,
    ) {
        viewState = LoadingData

        scope.launch {
            val announcementUpdateResult =
                announcementDetailsPresenter.registerUserToAnnouncement(
                    announcementId,
                    currentUser.name!!,
                    currentTakenSeats,
                    currentAvailableSeats,
                    registeredUsers,
                )

            viewState = when {
                announcementUpdateResult == "loading" -> LoadingData
                announcementUpdateResult == "success" -> UserRegistrationSuccess("Registration succeeded")
                announcementUpdateResult.contains("error") -> LoadingError("Network error")
                else -> LoadingError("Network error")
            }
        }
    }

    fun unregisterUserToAnnouncement(
        announcementId: String,
        currentTakenSeats: Int,
        currentAvailableSeats: Int,
        registeredUsers: MutableList<String>,
    ) {
        viewState = LoadingData

        scope.launch {
            val announcementUpdateResult =
                announcementDetailsPresenter.unregisterUserToAnnouncement(
                    announcementId,
                    currentUser.name!!,
                    currentTakenSeats,
                    currentAvailableSeats,
                    registeredUsers,
                )

            viewState = when {
                announcementUpdateResult == "loading" -> LoadingData
                announcementUpdateResult == "success" -> UserUnRegistrationSuccess("Unregistration succeeded")
                announcementUpdateResult.contains("error") -> LoadingError("Network error")
                else -> LoadingError("Network error")
            }
        }

    }
}