package hu.bme.aut.android.carpool.domain.model

data class Announcement(
    val id: String? = null,
    val ownerId: String? = null,
    val ownerName: String? =null,
    val timeOfDeparture: String? = null,
    val takenSeatsNumber: Int? = null,
    val freeSeatsNumber: Int? = null,
)
