package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R

@AndroidEntryPoint
class AddAnnouncementDialog :
    RainbowCakeDialogFragment<AddAnnouncementViewState, AddAnnouncementViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_actualities

    override fun render(viewState: AddAnnouncementViewState) {
        //TODO("Not yet implemented")
    }


}