package hu.bme.aut.android.carpool.domain.model.states

sealed class AnnouncementHandlingState<T> {
    class Loading<T> : AnnouncementHandlingState<T>()
    data class Success<T>(val data: T) : AnnouncementHandlingState<T>()
    data class Failed<T>(val message: String) : AnnouncementHandlingState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}