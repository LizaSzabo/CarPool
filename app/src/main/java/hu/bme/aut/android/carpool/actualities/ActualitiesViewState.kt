package hu.bme.aut.android.carpool.actualities

sealed class ActualitiesViewState()

object Initial : ActualitiesViewState()

class DataLoading() : ActualitiesViewState()

class DataReady() : ActualitiesViewState()

class NetworkError() : ActualitiesViewState()