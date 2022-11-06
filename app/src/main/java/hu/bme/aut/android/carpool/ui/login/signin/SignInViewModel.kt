package hu.bme.aut.android.carpool.ui.login.signin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInPresenter: SignInPresenter
) : RainbowCakeViewModel<SignInViewState>(Initial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun validateUser(userName: String, password: String) {
        scope.launch {
            viewState = when (val message = signInPresenter.loginUser(userName, password)) {
                "loading" -> Loading
                "success" -> LoginSuccess
                else -> LoginFail(message)
            }
        }
    }
}