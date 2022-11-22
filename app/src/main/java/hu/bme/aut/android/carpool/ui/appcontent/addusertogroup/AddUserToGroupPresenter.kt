package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class AddUserToGroupPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    suspend fun userExists(input: String): String? {
        var userId: String? = "-1"
        userInteractor.getUserByName(input).collect { state ->
            userId = when (state) {
                is BackendHandleState.Loading -> {
                    "-1"
                }
                is BackendHandleState.Success -> {
                    state.data.id
                }
                is BackendHandleState.Failed -> {
                    null
                }
            }
        }
        return userId
    }

    suspend fun saveUserIdToGroup(userId: String): String {
        return "error"
    }
}