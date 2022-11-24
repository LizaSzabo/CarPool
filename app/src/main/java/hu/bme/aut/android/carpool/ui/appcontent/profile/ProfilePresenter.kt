package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.graphics.Bitmap
import javax.inject.Inject

class ProfilePresenter @Inject constructor() {

    suspend fun addImageToUser(userName: String, image: Bitmap): String {
        return "error"
    }

}