package hu.bme.aut.android.carpool.ui.appcontent.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentGroupBinding
import hu.bme.aut.android.carpool.model.User


@AndroidEntryPoint
class GroupFragment : RainbowCakeFragment<GroupViewState, GroupViewModel>() {

    private lateinit var binding: FragmentGroupBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_group
    private lateinit var groupMemberListAdapter: GroupMemberListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun render(viewState: GroupViewState) {
        //TODO
    }

    private fun setupRecyclerView() {
        groupMemberListAdapter = GroupMemberListAdapter()
        binding.rvGroupMembers.layoutManager = LinearLayoutManager(context)
        binding.rvGroupMembers.adapter = groupMemberListAdapter
        groupMemberListAdapter.add(
            listOf(
                User(
                    "id",
                    "Name",
                )
            )
        )
    }
}