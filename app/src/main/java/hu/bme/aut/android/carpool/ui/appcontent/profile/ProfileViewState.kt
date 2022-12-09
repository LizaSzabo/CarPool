package hu.bme.aut.android.carpool.ui.appcontent.profile

import hu.bme.aut.android.carpool.domain.model.User

sealed class ProfileViewState()

object InitialProfileState : ProfileViewState()

object ProfileEditingState : ProfileViewState()

object LoadingState : ProfileViewState()

class ProfileSuccessfullyEditedState(val user: User) : ProfileViewState()

class ImageSavingError(val errorMessage: String) : ProfileViewState()

class ImageSavingSuccess(val successMessage: String) : ProfileViewState()

class LoadingError(val errorMessage: String) : ProfileViewState()