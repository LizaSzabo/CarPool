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
            val userName = addUserToGroupPresenter.userExists(input)
            if (userName != (-1).toString()) {
                if (userName != null) {
                    saveUser(userName)
                } else {
                    viewState = ErrorNoUser("$input is not an existing user name")
                }
            }
        }
    }

    private fun saveUser(userName: String) {
        scope.launch {
            val value = addUserToGroupPresenter.saveUserIdToGroup(userName)
            when {
                value == "success" -> {
                    viewState = UserAddedSuccess(userName)
                }
                value.contains("error") -> {
                    viewState = UserAddedError(value)
                }
            }
        }
    }
}
