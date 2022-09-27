package hu.bme.aut.android.carpool.actualities

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ActualitiesViewModel @Inject constructor(
    private val actualitiesPresenter: ActualitiesPresenter
) : RainbowCakeViewModel<ActualitiesViewState>(Initial) {}
