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

    private val uiScope = CoroutineScope(Dispatchers.Main)

    fun saveNewAnnouncement(announcement: Announcement) {
        uiScope.launch {
            viewState = when (addAnnouncementPresenter.saveNewAnnouncement(announcement)) {
                1 -> AddAnnouncementSaving()
                2 -> AddAnnouncementSuccess()
                3 -> AddAnnouncementFail()
                else -> AddAnnouncementFail()
            }
        }

    }
}