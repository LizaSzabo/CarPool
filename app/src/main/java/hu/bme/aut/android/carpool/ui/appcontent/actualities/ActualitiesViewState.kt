package hu.bme.aut.android.carpool.ui.appcontent.actualities

sealed class ActualitiesViewState()

object Initial : ActualitiesViewState()

class DataLoading() : ActualitiesViewState()

class DataReady() : ActualitiesViewState()

class NetworkError() : ActualitiesViewState()