package hu.bme.aut.android.carpool.domain.model.states

sealed class BackendHandleState<T> {
    class Loading<T> : BackendHandleState<T>()
    data class Success<T>(val data: T) : BackendHandleState<T>()
    data class Failed<T>(val message: String) : BackendHandleState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}