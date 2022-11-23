package hu.bme.aut.android.carpool.domain

import android.util.Log
import com.google.firebase.firestore.ktx.toObjects
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
        val userRef = userRepository.uploadUser(user)
        emit(BackendHandleState.success(userRef))
    }.catch {
        emit(BackendHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getUserByName(userName: String) = flow {
        emit(BackendHandleState.loading())
        val users: List<User> = userRepository.getUser(userName).toObjects()
        Log.i("users: ", users.toString())
        emit(BackendHandleState.success(users.first()))
    }.catch {
        emit(BackendHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addUserToGroupOfCurrentUser(userIdOfUserToAdd: String, groupToUpdate: MutableList<String>) = flow{
        emit(BackendHandleState.loading())
        userRepository.updateUserGroup(userIdOfUserToAdd, groupToUpdate)
        emit(BackendHandleState.success("update success"))
    }.catch {
        emit(BackendHandleState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}