package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
        viewState = ProfileSuccessfullyEditedState(currentUser, null, "")
    }

    fun saveImageToUser(bitmap: Bitmap) {
        viewState = LoadingState

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

    fun loadUserDetails() {
        viewState = LoadingState

        scope.launch {
            val userImageAsString = currentUser.name?.let { profilePresenter.getUserImage(it) }
            val decodedString: ByteArray = Base64.decode(userImageAsString, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            val announcementType = currentUser.name?.let { profilePresenter.getUserData(it) }
            currentUser.bitmap = decodedByte
            currentUser.ownAnnouncementId = announcementType?.ownAnnouncementId
            currentUser.registeredAnnouncementId = announcementType?.registeredAnnouncementId

            viewState =
                when {
                    currentUser.registeredAnnouncementId != null -> {
                        when (val announcement =
                            profilePresenter.getAnnouncement(currentUser.registeredAnnouncementId!!)) {
                            null -> LoadingError("Error while loading registered announcement data!")
                            else -> ProfileSuccessfullyEditedState(
                                currentUser,
                                announcement,
                                "Registered on:"
                            )
                        }
                    }
                    currentUser.ownAnnouncementId != null -> {
                        when (val announcement =
                            profilePresenter.getAnnouncement(currentUser.ownAnnouncementId!!)) {
                            null -> LoadingError("Error while loading own announcement data!")
                            else -> ProfileSuccessfullyEditedState(
                                currentUser,
                                announcement,
                                "Your announcement:"
                            )
                        }
                    }
                    else -> ProfileSuccessfullyEditedState(
                        currentUser,
                        null,
                        ""
                    )

                }
        }
    }
}