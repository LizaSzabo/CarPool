package hu.bme.aut.android.carpool.ui.appcontent.group

sealed class GroupViewState

object GroupInitialState : GroupViewState()

object GroupLoading : GroupViewState()

class GroupLoaded(val members: MutableList<String>) : GroupViewState()

class UserDeleteSuccess(val members: MutableList<String>) : GroupViewState()

class UserDeleteError(val error: String) : GroupViewState()