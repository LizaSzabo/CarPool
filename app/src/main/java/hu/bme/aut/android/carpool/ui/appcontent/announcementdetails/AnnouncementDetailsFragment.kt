package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentAnnouncementDetailsBinding
import hu.bme.aut.android.carpool.domain.model.Announcement


@AndroidEntryPoint
class AnnouncementDetailsFragment :
    RainbowCakeFragment<AnnouncementDetailsViewState, AnnouncementDetailsViewModel>() {

    private lateinit var binding: FragmentAnnouncementDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_announcement_details
    private val args: AnnouncementDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnnouncementDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAnnouncementDetails(args.announcementId)
    }

    override fun render(viewState: AnnouncementDetailsViewState) {
        when (viewState) {
            is DataLoaded -> {
                binding.loading.isVisible = false
                showContent(viewState.announcement)
                setupRegisterButton(viewState.announcement)
                setupUnRegisterButton(viewState.announcement)
                binding.buttonRegister.isEnabled =
                    viewState.announcement.freeSeatsNumber != 0 && !viewModel.checkUserAlreadyRegistered(
                        viewState.announcement
                    )
                binding.buttonDeleteRegistration.isEnabled = !binding.buttonRegister.isEnabled
            }
            LoadingData -> {
                binding.loading.isVisible = true
                binding.buttonRegister.isEnabled = false
                binding.buttonDeleteRegistration.isEnabled = false
            }
            is LoadingError -> {
                binding.loading.isVisible = false
                binding.buttonRegister.isEnabled = true
                binding.buttonDeleteRegistration.isEnabled = !binding.buttonRegister.isEnabled
            }
            Initial -> {
                binding.loading.isVisible = false
                binding.buttonRegister.isEnabled = false
                binding.buttonDeleteRegistration.isEnabled = false
            }
            is UserRegistrationSuccess -> {
                binding.loading.isVisible = false
                Toast.makeText(activity, viewState.successMessage, Toast.LENGTH_LONG).show()
                binding.buttonRegister.isEnabled = false
                viewModel.loadAnnouncementDetails(args.announcementId)
                binding.buttonDeleteRegistration.isEnabled = true
            }
            is UserUnRegistrationSuccess -> {
                binding.loading.isVisible = false
                Toast.makeText(activity, viewState.successMessage, Toast.LENGTH_LONG).show()
                binding.buttonRegister.isEnabled = true
                viewModel.loadAnnouncementDetails(args.announcementId)
                binding.buttonDeleteRegistration.isEnabled = false
            }
        }
    }

    private fun showContent(announcement: Announcement) {
        binding.ownerName.text = announcement.owner?.email
        binding.timeOfDepartureText.text = announcement.timeOfDeparture
        binding.takenSeats.text = announcement.takenSeatsNumber.toString()
    }

    private fun setupRegisterButton(announcement: Announcement) {
        binding.buttonRegister.setOnClickListener {
            viewModel.registerUserToAnnouncement(
                args.announcementId,
                announcement.takenSeatsNumber ?: 0,
                announcement.freeSeatsNumber ?: 0,
                announcement.registeredUsers as MutableList<String>,
            )
        }
    }

    private fun setupUnRegisterButton(announcement: Announcement) {
        binding.buttonDeleteRegistration.setOnClickListener {
            viewModel.unregisterUserToAnnouncement(
                args.announcementId,
                announcement.takenSeatsNumber ?: 0,
                announcement.freeSeatsNumber ?: 0,
                announcement.registeredUsers as MutableList<String>,
            )
        }
    }


}