package hu.bme.aut.android.carpool.ui.login.signin

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentSigninBinding

@AndroidEntryPoint
class SignInFragment : RainbowCakeFragment<SignInViewState, SignInViewModel>() {

    private lateinit var binding: FragmentSigninBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_signin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRegisterLink()
        setupLoginButton()
    }

    override fun onStart() {
        super.onStart()

        // viewModel.load()
    }

    override fun render(viewState: SignInViewState) {
        // TODO Render state
    }

    private fun setupRegisterLink() {
        val mSpannableString = SpannableString(binding.registrationLink.text)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        binding.registrationLink.text = mSpannableString

        binding.registrationLink.setOnClickListener {
            Toast.makeText(context, "Go to registration", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            if (binding.userNameInput.text.isEmpty()) {
                binding.userNameInput.error = "User name cannot be empty!"
            }
            if (binding.passwordInput.text.isEmpty()) {
                binding.passwordInput.error = "Password cannot be empty!"
            }
            if (viewModel.validateUser()) {
                val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                findNavController().navigate(action)
            }
        }
    }
}