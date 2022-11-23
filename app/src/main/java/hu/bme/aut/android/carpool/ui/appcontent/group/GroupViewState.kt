package hu.bme.aut.android.carpool.ui.appcontent.group

import hu.bme.aut.android.carpool.domain.model.User

sealed class GroupViewState

object GroupInitialState : GroupViewState()

object GroupLoading : GroupViewState()

class GroupLoaded(val members: MutableList<String>) : GroupViewState()