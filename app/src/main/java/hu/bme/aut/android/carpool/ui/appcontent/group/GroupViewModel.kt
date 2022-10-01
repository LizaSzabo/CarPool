package hu.bme.aut.android.carpool.ui.appcontent.group

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupPresenter: GroupPresenter
) : RainbowCakeViewModel<GroupViewState>(GroupInitialState) {}
