package hu.bme.aut.android.carpool.domain.model

import android.graphics.Bitmap

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val bitmap: Bitmap? = null,
    val hasTodayAnnouncement: Boolean? = null,
    val group: MutableList<String> = mutableListOf(),
)