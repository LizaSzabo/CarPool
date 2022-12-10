package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.domain.model.Announcement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddAnnouncementViewModel @Inject constructor(
    private val addAnnouncementPresenter: AddAnnouncementPresenter
) : RainbowCakeViewModel<AddAnnouncementViewState>(Initial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun saveNewAnnouncement(announcement: Announcement) {
        scope.launch {
            viewState = when (addAnnouncementPresenter.saveNewAnnouncement(announcement)) {
                "saving" -> AddAnnouncementSaving()
                "success" -> {
                    when (announcement.id?.let {
                        addAnnouncementPresenter.addAnnouncementToItsUser(
                            it
                        )
                    }) {
                        "saving" -> AddAnnouncementSaving()
                        "success" -> AddAnnouncementSuccess()
                        else -> AddAnnouncementFail()
                    }
                }
                "error" -> AddAnnouncementFail()
                else -> AddAnnouncementFail()
            }
        }

    }
}