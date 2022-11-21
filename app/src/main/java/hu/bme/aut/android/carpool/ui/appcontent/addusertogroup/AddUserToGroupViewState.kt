package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import hu.bme.aut.android.carpool.domain.model.User

sealed class AddUserToGroupViewState

object Initial : AddUserToGroupViewState()

class UserAddedSuccess(val user: User) : AddUserToGroupViewState()

class UserAddedError(val errorMessage: String) : AddUserToGroupViewState()