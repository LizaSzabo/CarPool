package hu.bme.aut.android.carpool.domain.model

import android.graphics.Bitmap

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    var bitmap: Bitmap? = null,
    val hasTodayAnnouncement: Boolean? = null,
    var group: MutableList<String> = mutableListOf(),
    var ownAnnouncementId: String? = null,
    var registeredAnnouncementId: String? = null,
)