package hu.bme.aut.android.carpool.ui.appcontent.group

import android.util.Log
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class GroupPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    suspend fun getUserGroup(userName: String): MutableList<String>? {
        var group: MutableList<String>? = mutableListOf()
        userInteractor.getUserByName(userName).collect { state ->
            group = when (state) {
                is BackendHandleState.Loading -> {
                    mutableListOf()
                }
                is BackendHandleState.Success -> {
                    state.data.group
                }
                is BackendHandleState.Failed -> {
                    null
                }
            }
        }
        return group
    }

    suspend fun deleteUserFromGroup(userName: String, email: String): MutableList<String>? {
        var groupAfterDelete: MutableList<String>? = mutableListOf()
        val updatedGroup = currentUser.group
        val index = currentUser.group.indexOf(userName)
        updatedGroup.removeAt(index)
        currentUser.email?.let {
            userInteractor.updateGroupOfCurrentUser(it, updatedGroup).collect { state ->
                groupAfterDelete = when (state) {
                    is BackendHandleState.Loading -> {
                        mutableListOf()
                    }
                    is BackendHandleState.Success -> {
                        currentUser.group.remove(userName)
                        updatedGroup
                    }
                    is BackendHandleState.Failed -> {
                        null
                    }
                }
            }
        }
        return groupAfterDelete
    }

    suspend fun getEmailByName(userName: String): String? {
        var userId: String? = "-1"
        userInteractor.getUserByName(userName).collect { state ->
            userId = when (state) {
                is BackendHandleState.Loading -> {
                    "-1"
                }
                is BackendHandleState.Success -> {
                    state.data.email
                }
                is BackendHandleState.Failed -> {
                    null
                }
            }
        }
        return userId
    }

}