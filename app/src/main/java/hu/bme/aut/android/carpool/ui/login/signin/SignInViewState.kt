package hu.bme.aut.android.carpool.ui.login.signin

sealed class SignInViewState

object Initial : SignInViewState()
object Loading : SignInViewState()
object LoginSuccess : SignInViewState()
class LoginFail(val message: String) : SignInViewState()