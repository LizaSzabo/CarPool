package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.graphics.Bitmap
import android.util.Base64
import hu.bme.aut.android.carpool.domain.UserInteractor
import hu.bme.aut.android.carpool.domain.model.states.BackendHandleState
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    suspend fun addImageToUser(userEmail: String, image: Bitmap): String {
        var updateMessage = "loading"

        encodeImage(image)?.let {
            userInteractor.updateUserImage(userEmail, it).collect { state ->
                updateMessage = when (state) {
                    is BackendHandleState.Loading -> {
                        "loading"
                    }
                    is BackendHandleState.Success -> {
                        "success"
                    }
                    is BackendHandleState.Failed -> {
                        "error: " + state.message
                    }
                }
            }
        }
        return updateMessage
    }

    suspend fun getUserImage(userName: String): String? {
        var userId: String? = "-1"
        userInteractor.getUserImage(userName).collect { state ->
            userId = when (state) {
                is BackendHandleState.Loading -> {
                    null
                }
                is BackendHandleState.Success -> {
                    state.data.toString()
                }
                is BackendHandleState.Failed -> {
                    null
                }
            }
        }
        return userId
    }


    private fun encodeImage(bm: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}