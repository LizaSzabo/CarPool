package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>() {

    private lateinit var binding: FragmentProfileBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

    override fun render(viewState: ProfileViewState) {
        when (viewState) {
            is InitialProfileState -> {
                binding.userNameEditText.visibility = View.INVISIBLE
                binding.userNameText.visibility = View.VISIBLE
                binding.profileImage.isEnabled = false
                binding.addGroupMemberButton.visibility = View.INVISIBLE
            }
            is ProfileEditingState -> {
                binding.userNameEditText.visibility = View.VISIBLE
                binding.userNameText.visibility = View.INVISIBLE
                binding.profileImage.isEnabled = true
                binding.addGroupMemberButton.visibility = View.VISIBLE
            }
            is ProfileSuccessfullyEditedState -> {
                binding.userNameEditText.visibility = View.INVISIBLE
                binding.userNameText.visibility = View.VISIBLE
                binding.profileImage.isEnabled = false
                binding.addGroupMemberButton.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfileButton.setOnClickListener {
            if (!binding.editProfileButton.isChecked) {
                viewModel.switchToSavedMode()
            } else {
                viewModel.switchToEditMode()
            }

        }

    }


}