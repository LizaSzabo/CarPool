package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

sealed class AddAnnouncementViewState()

object Initial : AddAnnouncementViewState()

class AddAnnouncementSaving : AddAnnouncementViewState()

class AddAnnouncementSuccess : AddAnnouncementViewState()

class AddAnnouncementFail : AddAnnouncementViewState()