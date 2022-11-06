package hu.bme.aut.android.carpool.ui.login.signin

import hu.bme.aut.android.carpool.domain.LoginInteractor
import hu.bme.aut.android.carpool.domain.model.states.LoginHandleState
import javax.inject.Inject

class SignInPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor
) {

    suspend fun loginUser(userEmail: String, password: String): String {
        var message = ""
        loginInteractor.loginUser(userEmail, password).collect { state ->
           message = when (state) {
                is LoginHandleState.Loading -> {
                    "loading"
                }
                is LoginHandleState.Success -> {
                    "success"
                }
                is LoginHandleState.Failed -> {
                    state.message
                }
                is LoginHandleState.FailedNoUser -> {
                    state.message
                }
            }
        }
        return message
    }
}