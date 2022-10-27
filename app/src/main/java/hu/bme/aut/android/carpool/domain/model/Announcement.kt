package hu.bme.aut.android.carpool.domain.model

data class Announcement(
    val ownerId: String,
    val ownerName: String,
    val timeOfDeparture: String,
    val takenSeatsNumber: Int,
    val freeSeatsNumber: Int,
)
