package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hu.bme.aut.android.carpool.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepository @Inject constructor() {
    suspend fun uploadUser(user: User) {
        FirebaseFirestore.getInstance().collection("users").document(user.email!!).set(user)
            .await()
    }

    suspend fun getUser(userName: String): QuerySnapshot =
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("name", userName).get()
            .await()

    suspend fun updateUserGroup(email: String, group: MutableList<String>) {
        FirebaseFirestore.getInstance().collection("users").document(email)
            .update("group", group).await()
    }

    suspend fun updateUserImage(email: String, bitmap: String) {
        FirebaseFirestore.getInstance().collection("users").document(email)
            .update("bitmap", bitmap).await()
    }

    suspend fun addUserAnnouncement(email: String, announcementId: String) {
        FirebaseFirestore.getInstance().collection("users").document(email)
            .update("ownAnnouncementId", announcementId).await()
    }
}