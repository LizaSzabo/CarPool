package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

sealed class AddUserToGroupViewState

object Initial : AddUserToGroupViewState()

class UserAddedSuccess(val userId: String) : AddUserToGroupViewState()

class UserAddedError(val errorMessage: String) : AddUserToGroupViewState()

class ErrorNoUser(val errorMessage: String) : AddUserToGroupViewState()