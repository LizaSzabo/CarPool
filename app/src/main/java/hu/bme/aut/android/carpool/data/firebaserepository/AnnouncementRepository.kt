package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hu.bme.aut.android.carpool.domain.model.Announcement
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnnouncementRepository @Inject constructor() {
    suspend fun uploadAnnouncement(announcement: Announcement) {
        FirebaseFirestore.getInstance().collection("announcements")
            .document(announcement.id!!).set(announcement).await()
    }

    fun getTodayAnnouncements() =
        FirebaseFirestore.getInstance().collection("announcements")

    suspend fun getAnnouncement(announcementId: String): QuerySnapshot =
        FirebaseFirestore.getInstance().collection("announcements")
            .whereEqualTo("id", announcementId).get()
            .await()

    suspend fun updateAnnouncement(
        announcementId: String,
        registeredUsers: MutableList<String>,
        takenSeatsNumber: Int,
        availableSeatsNumber: Int
    ) {
        FirebaseFirestore.getInstance().collection("announcements").document(announcementId)
            .update("registeredUsers", registeredUsers).await()
        FirebaseFirestore.getInstance().collection("announcements").document(announcementId)
            .update("takenSeatsNumber", takenSeatsNumber).await()
        FirebaseFirestore.getInstance().collection("announcements").document(announcementId)
            .update("freeSeatsNumber", availableSeatsNumber).await()
    }
}