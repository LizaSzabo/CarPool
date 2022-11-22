package hu.bme.aut.android.carpool.domain

import hu.bme.aut.android.carpool.data.firebaserepository.UserRepository
import hu.bme.aut.android.carpool.domain.model.User
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun uploadUser(user: User) = flow {
        emit(BackendHandleState.loading())
        val postRef = userRepository.uploadUser(user)
        emit(BackendHandleState.success(postRef))
    }.catch {
        emit(BackendHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


}