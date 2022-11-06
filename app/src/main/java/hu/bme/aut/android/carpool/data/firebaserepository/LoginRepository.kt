package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepository @Inject constructor() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(mail: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(mail, password)
    }

    fun register(mail: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(mail, password)
    }
}