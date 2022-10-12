package hu.bme.aut.android.carpool.data.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FirebaseAuthentication {
    private val firebaseUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    val uid: String?
        get() = firebaseUser?.uid

    val userName: String?
        get() = firebaseUser?.displayName

    val userEmail: String?
        get() = firebaseUser?.email
}