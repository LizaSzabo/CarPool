package hu.bme.aut.android.carpool.domain.model.states

sealed class LoginHandleState<T> {
    class Loading<T> : LoginHandleState<T>()
    data class Success<T>(val data: T) : LoginHandleState<T>()
    data class Failed<T>(val message: String) : LoginHandleState<T>()
    data class FailedNoUser<T>(val message: String) : LoginHandleState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
        fun <T> failedNoUser(message: String) = Failed<T>(message)
    }
}