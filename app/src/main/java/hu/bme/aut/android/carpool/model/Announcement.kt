package hu.bme.aut.android.carpool.model

data class Announcement(
    val ownerId: String,
    val ownerName: String,
    val timeOfDeparture: String,
    val takenSeatsNumber: Int,
    val freeSeatsNumber: Int,
)
