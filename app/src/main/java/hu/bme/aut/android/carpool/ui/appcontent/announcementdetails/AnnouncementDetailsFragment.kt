package hu.bme.aut.android.carpool.ui.appcontent.announcementdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        Log.i("announcementId", args.announcementId)
        viewModel.loadAnnouncementDetails(args.announcementId)
    }

    override fun render(viewState: AnnouncementDetailsViewState) {
        when (viewState) {
            is DataLoaded -> {
                binding.loading.isVisible = false
                showContent(viewState.announcement)
            }
            LoadingData -> {
                binding.loading.isVisible = true
            }
            is LoadingError -> {
                binding.loading.isVisible = false
            }
            Initial -> {
                binding.loading.isVisible = false
            }
        }
    }

    private fun showContent(announcement: Announcement) {
        binding.ownerName.text = announcement.owner?.email
        binding.timeOfDepartureText.text = announcement.timeOfDeparture
        binding.takenSeats.text = announcement.takenSeatsNumber.toString()
    }
}