package hu.bme.aut.android.carpool.ui.appcontent.actualities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentActualitiesBinding
import hu.bme.aut.android.carpool.model.Announcement


@AndroidEntryPoint
class ActualitiesFragment : RainbowCakeFragment<ActualitiesViewState, ActualitiesViewModel>() {

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

        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""

        setupRecyclerView()

    }

    override fun render(viewState: ActualitiesViewState) {
        //TODO
    }

    private fun setupRecyclerView() {
        actualitiesListAdapter = ActualitiesListAdapter()
        binding.rvActualities.layoutManager = LinearLayoutManager(context)
        binding.rvActualities.adapter = actualitiesListAdapter
        actualitiesListAdapter.addAllAnnouncements(
            listOf(
                Announcement(
                    "id",
                    "Name",
                    "10:00",
                    1,
                    4
                )
            )
        )
    }
}