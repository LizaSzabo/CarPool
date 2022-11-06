package hu.bme.aut.android.carpool.ui.login.register

import hu.bme.aut.android.carpool.domain.LoginInteractor
import hu.bme.aut.android.carpool.domain.model.states.LoginHandleState
import hu.bme.aut.android.carpool.domain.model.states.RegistrationHandleState
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor
) {
    suspend fun registerUser(userEmail: String, password: String): String {
        var message = ""
        loginInteractor.registerUser(userEmail, password).collect { state ->
            message = when (state) {
                is RegistrationHandleState.Loading -> {
                    "loading"
                }
                is RegistrationHandleState.Success -> {
                    "success"
                }
                is RegistrationHandleState.Failed -> {
                    state.message
                }
            }
        }
        return message
    }
}