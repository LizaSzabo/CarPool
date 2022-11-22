package hu.bme.aut.android.carpool.ui.login.register

import hu.bme.aut.android.carpool.domain.LoginInteractor
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.User
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import hu.bme.aut.android.carpool.domain.model.states.RegistrationHandleState
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor,
    private val userInteractor: UserInteractor
) {
    suspend fun registerUser(userEmail: String, password: String): String {
        var message = ""
        loginInteractor.registerUser(userEmail, password).collect { state ->
            message = when (state) {
                is RegistrationHandleState.Loading -> {
                    "loading"
                }
                is RegistrationHandleState.Success -> {
                    createUser(userEmail)
                }
                is RegistrationHandleState.Failed -> {
                    state.message
                }
            }
        }
        return message
    }


    private suspend fun createUser(email: String): String {
        var message = "success"
        val user = User(email = email, name = email.substringBefore("@"))
        userInteractor.uploadUser(user).collect { state ->
            message = when (state) {
                is BackendHandleState.Loading -> {
                    "loading"
                }
                is BackendHandleState.Success -> {
                    "success"
                }
                is BackendHandleState.Failed -> {
                    state.message
                }
            }
        }
        return message
    }
}