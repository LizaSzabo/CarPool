package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hu.bme.aut.android.carpool.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor() {
    suspend fun uploadUser(user: User): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").add(user).await()
    }

    suspend fun getUser(userName: String): QuerySnapshot =
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("userName", userName).get()
            .await()
}