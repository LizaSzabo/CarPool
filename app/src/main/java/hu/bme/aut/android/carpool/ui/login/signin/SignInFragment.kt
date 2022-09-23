package hu.bme.aut.android.carpool.ui.login.signin

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R

@AndroidEntryPoint
class SignInFragment : RainbowCakeFragment<SignInViewState, SignInViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_signin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Setup views
    }

    override fun onStart() {
        super.onStart()

        // viewModel.load()
    }

    override fun render(viewState: SignInViewState) {
        // TODO Render state
    }
}