package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddUserToGroupViewModel @Inject constructor(
    private val addUserToGroupPresenter: AddUserToGroupPresenter
) : RainbowCakeViewModel<AddUserToGroupViewState>(Initial) {}
