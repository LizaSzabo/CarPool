package hu.bme.aut.android.carpool.ui.appcontent.actualities

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.domain.model.Announcement
import hu.bme.aut.android.carpool.domain.model.states.AnnouncementHandlingState
import hu.bme.aut.android.carpool.ui.appcontent.addannouncement.AddAnnouncementFail
import hu.bme.aut.android.carpool.ui.appcontent.addannouncement.AddAnnouncementSaving
import hu.bme.aut.android.carpool.ui.appcontent.addannouncement.AddAnnouncementSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ActualitiesViewModel @Inject constructor(
    private val actualitiesPresenter: ActualitiesPresenter
) : RainbowCakeViewModel<ActualitiesViewState>(Initial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun loadAnnouncements() {
        viewState = DataLoading()

        scope.launch {
            val announcements = actualitiesPresenter.getTodayAnnouncement()
            Timber.i("announcements", announcements.toString())
            viewState = when {
                announcements == null ->  NetworkError()
                else -> DataReady(announcements as List<Announcement>)
               // else -> DataLoading()
            }
        }
    }
}
