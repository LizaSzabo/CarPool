package hu.bme.aut.android.carpool.ui.appcontent.group

import android.util.Log
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
        scope.launch {
            val group = currentUser.name?.let { groupPresenter.getUserGroup(it) }
            Log.i("group", currentUser.email.toString())
            if (group != null && group.size > 0) {
                currentUser.group = group
                viewState = GroupLoaded(group)
            }
        }
    }
}
