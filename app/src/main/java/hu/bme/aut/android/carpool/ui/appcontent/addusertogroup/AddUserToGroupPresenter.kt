package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import javax.inject.Inject

class AddUserToGroupPresenter @Inject constructor() {

    suspend fun userExists(input: String): Long? {
        return null
    }

    suspend fun saveUserIdToGroup(userId: Long): String {
        return "error"
    }
}