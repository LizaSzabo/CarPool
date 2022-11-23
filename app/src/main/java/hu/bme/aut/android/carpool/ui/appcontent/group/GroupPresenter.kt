package hu.bme.aut.android.carpool.ui.appcontent.group

import android.util.Log
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import javax.inject.Inject

class GroupPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    suspend fun getUserGroup(userName: String): MutableList<String>? {
        var userId : MutableList<String>? = mutableListOf()
        userInteractor.getUserByName(userName).collect { state ->
            Log.i("getUserGroupState", state.toString())
            userId = when (state) {
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
        return userId
    }

}