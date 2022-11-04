package hu.bme.aut.android.carpool.ui.appcontent.actualities

import hu.bme.aut.android.carpool.domain.model.Announcement

sealed class ActualitiesViewState()

object Initial : ActualitiesViewState()

class DataLoading : ActualitiesViewState()

class DataReady(val announcements: List<Announcement>) : ActualitiesViewState()

class NetworkError : ActualitiesViewState()