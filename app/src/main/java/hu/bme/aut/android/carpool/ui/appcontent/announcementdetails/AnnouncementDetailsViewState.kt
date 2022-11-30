package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import hu.bme.aut.android.carpool.domain.model.Announcement

sealed class AnnouncementDetailsViewState

object Initial : AnnouncementDetailsViewState()

object LoadingData : AnnouncementDetailsViewState()

class DataLoaded(val announcement: Announcement) : AnnouncementDetailsViewState()

class LoadingError(val errorMessage: String) : AnnouncementDetailsViewState()
