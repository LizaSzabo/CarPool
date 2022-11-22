package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddUserToGroupViewModel @Inject constructor(
    private val addUserToGroupPresenter: AddUserToGroupPresenter
) : RainbowCakeViewModel<AddUserToGroupViewState>(Initial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun addUserToGroup(input: String) {
        checkUserExists(input)
    }

    private fun checkUserExists(input: String) {
        scope.launch {
            val userId = addUserToGroupPresenter.userExists(input)
            if (userId != (-1).toString()) {
                if (userId != null) {
                    saveUser(userId)
                } else {
                    viewState = ErrorNoUser("$input is not an existing user name")
                }
            }
        }
    }

    private fun saveUser(userId: String) {
        scope.launch {
            when (addUserToGroupPresenter.saveUserIdToGroup(userId)) {
                "success" -> {
                    viewState = UserAddedSuccess(userId)
                }
                "error" -> {
                    viewState = UserAddedError("Error on user adding to group")
                }
            }
        }
    }
}
