package hu.bme.aut.android.carpool.domain.model.states

sealed class RegistrationHandleState<T> {
    class Loading<T> : RegistrationHandleState<T>()
    data class Success<T>(val data: T) : RegistrationHandleState<T>()
    data class Failed<T>(val message: String) : RegistrationHandleState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}