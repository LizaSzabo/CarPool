package hu.bme.aut.android.carpool.ui.appcontent.profile

sealed class ProfileViewState()

object InitialProfileState : ProfileViewState()

object ProfileEditingState : ProfileViewState()

object ProfileSuccessfullyEditedState : ProfileViewState()

class ImageSavingError(val errorMessage: String) : ProfileViewState()

class ImageSavingSuccess(val successMessage: String) : ProfileViewState()