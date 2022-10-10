package hu.bme.aut.android.carpool.ui.appcontent.profile

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(InitialProfileState) {

    fun switchToEditMode() {
        viewState = ProfileEditingState
    }

    fun switchToSavedMode() {
        viewState = ProfileSuccessfullyEditedState
    }
}