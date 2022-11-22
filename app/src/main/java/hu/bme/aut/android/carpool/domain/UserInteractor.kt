package hu.bme.aut.android.carpool.domain

import hu.bme.aut.android.carpool.data.firebaserepository.UserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
){

}