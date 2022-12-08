package hu.bme.aut.android.carpool.ui.appcontent.actualities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentActualitiesBinding
import hu.bme.aut.android.carpool.domain.model.Announcement
import timber.log.Timber


@AndroidEntryPoint
class ActualitiesFragment : RainbowCakeFragment<ActualitiesViewState, ActualitiesViewModel>(),
    ActualitiesListAdapter.AnnouncementClickListener {

    private lateinit var binding: FragmentActualitiesBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_actualities
    private lateinit var actualitiesListAdapter: ActualitiesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActualitiesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFloatingButton()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            "fromNewAnnouncement"
        )?.observe(viewLifecycleOwner) {
            refresh()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAnnouncements()
    }

    private fun refresh() {
        Timber.i("ActualitiesFragment refresh")

        viewModel.loadAnnouncements()
    }

    override fun render(viewState: ActualitiesViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = true
            }
            is DataLoading -> {
                binding.loading.isVisible = true
            }
            is DataReady -> {
                binding.loading.isVisible = false
                Timber.i("Actualities DataReady")
                actualitiesListAdapter.addAllAnnouncements(viewState.announcements)
            }
            is NetworkError -> {
                binding.loading.isVisible = false
                Timber.i("Actualities NetworkError")
            }
        }
    }

    private fun setupRecyclerView() {
        actualitiesListAdapter = ActualitiesListAdapter()
        actualitiesListAdapter.itemClickListener = this
        binding.rvActualities.layoutManager = LinearLayoutManager(context)
        binding.rvActualities.adapter = actualitiesListAdapter
    }

    private fun setupFloatingButton() {
        binding.fab.setOnClickListener {
            val action =
                ActualitiesFragmentDirections.actionActualitiesFragmentToAddAnnouncementDialog()
            findNavController().navigate(action)
        }
    }

    override fun onItemClickListener(announcement: Announcement) {
        announcement.id?.let {
            findNavController().navigate(
                ActualitiesFragmentDirections.actionActualitiesFragmentToAnnouncementDetailsFragment(
                    announcement.id
                )
            )
        }
    }
}