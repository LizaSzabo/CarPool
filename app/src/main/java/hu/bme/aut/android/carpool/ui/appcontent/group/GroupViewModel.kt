package hu.bme.aut.android.carpool.ui.appcontent.group

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupPresenter: GroupPresenter
) : RainbowCakeViewModel<GroupViewState>(GroupInitialState) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun loadUsersList() {
        viewState = GroupLoading
        scope.launch {
            val group = currentUser.name?.let { groupPresenter.getUserGroup(it) }
            if (group != null) {
                currentUser.group = group
                viewState = GroupLoaded(group)
            }
        }
    }

    fun deleteUserFromGroup(userName: String) {
        viewState = GroupLoading
        scope.launch {
            val userEmail = groupPresenter.getEmailByName(userName)
            if (userEmail == null) {
                viewState = UserDeleteError("Error: delete not succeeded")
            } else if (userEmail != "-1") {
                val value = groupPresenter.deleteUserFromGroup(userName, userEmail)
                viewState = when {
                    value != null -> {
                        UserDeleteSuccess(value)
                    }
                    else -> {
                        UserDeleteError("Error: delete not succeeded")
                    }
                }
            }
        }
    }
}
