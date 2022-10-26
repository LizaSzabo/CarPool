package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddAnnouncementViewModel @Inject constructor(
    private val addAnnouncementPresenter: AddAnnouncementPresenter
) : RainbowCakeViewModel<AddAnnouncementViewState>(Initial) {}