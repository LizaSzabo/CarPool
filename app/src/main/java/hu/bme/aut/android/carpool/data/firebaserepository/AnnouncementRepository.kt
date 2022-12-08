package hu.bme.aut.android.carpool.data.firebaserepository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.android.carpool.domain.model.Announcement
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnnouncementRepository @Inject constructor() {
    suspend fun uploadAnnouncement(announcement: Announcement): DocumentReference {
        return FirebaseFirestore.getInstance().collection("announcements").add(announcement).await()
    }

    fun getTodayAnnouncements() =
        FirebaseFirestore.getInstance().collection("announcements")

    suspend fun getAnnouncement(announcementId: String) =
        FirebaseFirestore.getInstance().collection("announcements")
            .whereEqualTo("id", announcementId).get()
            .await()
}