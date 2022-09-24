package hu.bme.aut.android.carpool.ui.login.signin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInPresenter: SignInPresenter
) : RainbowCakeViewModel<SignInViewState>(Initial) {

    fun validateUser(): Boolean{
        //TODO: add actual authentication
        return true
    }
}