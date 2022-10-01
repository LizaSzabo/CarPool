package hu.bme.aut.android.carpool.ui.appcontent.profile

sealed class ProfileViewState()

object InitialProfileState : ProfileViewState()

object ProfileEditingState : ProfileViewState()

object ProfileSuccessfullyEditedState : ProfileViewState()