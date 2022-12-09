package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.graphics.Bitmap
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(InitialProfileState) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun switchToEditMode() {
        viewState = ProfileEditingState
    }

    fun switchToSavedMode() {
        viewState = ProfileSuccessfullyEditedState
    }

    fun saveImageToUser(bitmap: Bitmap) {
        scope.launch {
            val savingResult =
                currentUser.email?.let { profilePresenter.addImageToUser(it, bitmap) }
            when {
                savingResult == "success" -> {
                    currentUser.bitmap = bitmap
                    viewState = ImageSavingSuccess("Image added successfully")
                }
                savingResult == "error" -> {
                    viewState = ImageSavingError(savingResult)
                }
                savingResult?.contains("error") == true -> {
                    viewState = ImageSavingError(savingResult)
                }
            }
        }
    }
}