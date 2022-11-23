package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepository @Inject constructor() {
    suspend fun uploadUser(user: User): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").add(user).await()
    }

    suspend fun getUser(userName: String): QuerySnapshot =
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("name", userName).get()
            .await()

    suspend fun updateUserGroup(userId: String, group: MutableList<String>) =
        currentUser.id?.let {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("group", group).snapshots().first().documents[0].reference.update("group", group)
                .await()
        }
}