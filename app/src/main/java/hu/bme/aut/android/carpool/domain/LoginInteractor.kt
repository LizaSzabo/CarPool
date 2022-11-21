package hu.bme.aut.android.carpool.domain

import android.util.Log
import com.google.firebase.auth.AuthResult
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.firebaseAuth
import hu.bme.aut.android.carpool.data.firebaserepository.LoginRepository
import hu.bme.aut.android.carpool.domain.model.states.LoginHandleState
import hu.bme.aut.android.carpool.domain.model.states.RegistrationHandleState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun loginUser(mail: String, password: String) = flow {
        emit(LoginHandleState.loading())

        val loginResult: AuthResult = loginRepository.signIn(mail, password).await()

        if (loginResult.user != null) {
            Log.i("currentUseeeer", firebaseAuth.userEmail.toString())
            emit(LoginHandleState.success(true))
        } else emit(LoginHandleState.failedNoUser("User doesn't exist"))
    }.catch {
        emit(LoginHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun registerUser(mail: String, password: String) = flow {
        emit(RegistrationHandleState.loading())

        loginRepository.register(mail, password).await()

        emit(RegistrationHandleState.success(true))
    }.catch {
        emit(RegistrationHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}