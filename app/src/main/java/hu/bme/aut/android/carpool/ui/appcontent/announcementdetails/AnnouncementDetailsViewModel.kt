package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
}