package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.model.Announcement
import javax.inject.Inject


@HiltViewModel
class AddAnnouncementViewModel @Inject constructor(
    private val addAnnouncementPresenter: AddAnnouncementPresenter
) : RainbowCakeViewModel<AddAnnouncementViewState>(Initial) {

    fun saveNewAnnouncement(announcement: Announcement) {
        viewState = AddAnnouncementSaving()

        //TODO: real value of save to firebase
        val success = true

        viewState = if (success) {
            AddAnnouncementSuccess()
        } else {
            AddAnnouncementFail()
        }
    }
}