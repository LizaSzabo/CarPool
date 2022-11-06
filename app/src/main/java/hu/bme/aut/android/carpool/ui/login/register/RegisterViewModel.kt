package hu.bme.aut.android.carpool.ui.login.register

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(RegisterInitial) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun registerUser(userEmail: String, password: String) {
        scope.launch {
            viewState = when (val message = registerPresenter.registerUser(userEmail, password)) {
                "loading" -> RegisterLoading
                "success" -> RegisterSuccess
                else -> RegisterFail(message)
            }
        }
    }
}