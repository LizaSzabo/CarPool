package hu.bme.aut.android.carpool.data.firebaserepository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hu.bme.aut.android.carpool.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepository @Inject constructor() {
    suspend fun uploadUser(user: User) {
        FirebaseFirestore.getInstance().collection("users").document(user.id!!).set(user)
            .await()//.add(user).await()
    }

    suspend fun getUser(userName: String): QuerySnapshot =
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("name", userName).get()
            .await()

    suspend fun updateUserGroup(userId: String, group: MutableList<String>) {
        Log.i(
            "documenttt: ",
            FirebaseFirestore.getInstance().collection("users").document("1NjNW1CTDuY2S2KB0hiq")
                .toString()
        )
        FirebaseFirestore.getInstance().collection("users").document("1NjNW1CTDuY2S2KB0hiq")
            .update("group", group).await()

    }
}